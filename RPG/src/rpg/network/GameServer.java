package rpg.network;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import network.Switchboard;
import network.TcpClient;
import network.event.ConnectListener;
import network.event.ConnectionEvent;
import network.event.DisconnectListener;
import network.message.Message;
import network.protocol.Protocol;
import rpg.element.Element;
import rpg.element.entity.Avatar;
import rpg.element.entity.Player;
import rpg.element.entity.race.Human;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.MultiAbilityDrawer;
import rpg.graphics.ShowInventory;
import rpg.graphics.Translate;
import rpg.logic.Tuple;
import rpg.logic.level.Game;
import rpg.logic.level.Level3;

public class GameServer {

	public static final int PORT = 1234;

	public static void main(String[] args) throws IOException {
		Game game = new Level3();
		GameServer server = new GameServer(game);
		server.start();
	}

	private static void execute(String string, Game game, TcpClient client) {
		String[] arr = string.split(" ");
		Player player = game.getPlayer(client).get();
		String command = arr[0];
		switch (command) {
		case "moveBy":
			game.getObstaclesFromMoveBy(player, Vector2D.valueOf(arr[1]));
			break;
		case "setLocation":
			player.setLocation(Vector2D.valueOf(arr[1]));
			break;
		case "jump":
			player.tryJump();
			break;
		case "fall":
			player.tryFall();
			break;
		case "moveHorizontally":
			player.moveHorizontally(Double.parseDouble(arr[1]));
			break;
		case "onClick":
			game.onClick(player, Vector2D.valueOf(arr[1]));
			break;
		case "cast":
			player.tryCast(game, Integer.parseInt(arr[1]));
			break;
		case "interact":
			game.tryInteract(player);
			break;
		}
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
		server = new Switchboard(PORT);
		server.addConnectListener(new ConnectListener() {

			@Override
			public void onConnect(ConnectionEvent e) {
				TcpClient c = e.getClient();
				game.addPlayer(c, new Human(), new Avatar());
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

	public void start() {
		new Thread(this::run, "Wait for clients/Update game").start();
	}

	public void startReceiving() {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				for (Tuple<String, TcpClient> command : receivedCommands) {
					if (isAllowed(command.getFirst())) {
						execute(command.getFirst(), game, command.getSecond());
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
		while (server.isRunning()) {
			long now = System.nanoTime();
			game.update((now - last) * 1e-9);
			last = now;
		}
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
			c.send(Message.data("dimensions " + new Vector2D(game.getGrid().getWidth(), game.getGrid().getHeight())));
		});
		server.send(Message.data("end"));
	}

}
