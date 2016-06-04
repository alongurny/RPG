package rpg.network;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import event.ConnectListener;
import event.ConnectionEvent;
import event.DisconnectListener;
import protocol.Protocol;
import rpg.element.Element;
import rpg.element.Player;
import rpg.element.entity.FireMage;
import rpg.element.entity.Human;
import rpg.graphics.Drawer;
import rpg.graphics.MultiAbilityDrawer;
import rpg.graphics.ShowInventory;
import rpg.graphics.Translate;
import rpg.logic.Tuple;
import rpg.logic.level.Game;
import rpg.logic.level.Level2;
import tcp.Switchboard;
import tcp.TcpClient;
import tcp.message.Message;

public class GameServer {

	public static void main(String[] args) throws IOException {
		Game game = new Level2();
		GameServer server = new GameServer(game);
		server.start();
	}

	private boolean firstConnection;
	private List<Tuple<String, TcpClient>> receivedCommands;
	private Switchboard server;
	private Timer timer;
	private Game game;
	private Protocol<Drawer, String> protocol;

	public GameServer(Game game) throws IOException {
		this.game = game;
		receivedCommands = new CopyOnWriteArrayList<>();
		timer = new Timer();
		protocol = new DrawerProtocol();
		server = new Switchboard();
		server.addConnectListener(new ConnectListener() {

			@Override
			public void onConnect(ConnectionEvent e) {
				TcpClient c = e.getClient();
				game.addPlayer(c, new Human(), new FireMage());
				if (game.isReady()) {
					firstConnection = true;
				}
			}
		});
		server.addDisconnectListener(new DisconnectListener() {

			@Override
			public void onDisconnect(ConnectionEvent e) {
				game.removePlayer(e.getClient());
			}
		});
		server.addMessageListener(e -> receivedCommands.add(Tuple.of(e.getMessage().getData(), e.getClient())));
	}

	public boolean isAllowed(String command) {
		if (command.startsWith("nothing")) {
			return true;
		}
		return true;
	}

	private void send() {
		server.send(Message.data("start"));
		for (Element e : game.getDynamicElements()) {
			Translate t = new Translate((int) e.getLocation().getX(), (int) e.getLocation().getY());
			server.send(Message.data("dynamic " + protocol.encode(t)));
			server.send(Message.data("dynamic " + e.getDrawer()));
			server.send(Message.data("dynamic " + protocol.encode(t.negate())));
		}
		if (firstConnection) {
			for (Element e : game.getStaticElements()) {
				String t1 = new Translate((int) e.getLocation().getX(), (int) e.getLocation().getY()).represent();
				String t2 = new Translate((int) -e.getLocation().getX(), (int) -e.getLocation().getY()).represent();
				server.send(Message.data("static " + t1));
				server.send(Message.data("static " + e.getDrawer()));
				server.send(Message.data("static " + t2));
			}
			firstConnection = false;
		}
		server.forEach(c -> {
			MultiAbilityDrawer mad = new MultiAbilityDrawer(120, 480);
			Player p = game.getPlayer(c).get();
			p.getAbilities().forEach(a -> mad.addAbility(p, a));
			c.send(Message.data("absolute " + mad.getDrawer()));
			c.send(Message.data("inventory " + new ShowInventory(160, 160, p).getDrawer()));
			c.send(Message.data("location " + p.getLocation()));
		});
		server.send(Message.data("end"));
	}

	private void run() {
		server.start();
		startReceiving();
		while (!game.isReady()) {
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		startSending();
		long last = System.nanoTime();
		while (true) {
			long now = System.nanoTime();
			game.update((now - last) * 1e-9);
			last = now;
		}
	}

	public void start() {
		new Thread(this::run).start();
	}

	public void startReceiving() {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				for (Tuple<String, TcpClient> command : receivedCommands) {
					if (isAllowed(command.getFirst())) {
						NetworkCommand.execute(command.getFirst(), game, command.getSecond());
					}
				}
				receivedCommands.clear();
			}
		}, 0, 30);
	}

	public void startSending() {
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				send();
			}
		}, 0, 30);
	}

}
