package rpg.network;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.SwingUtilities;

import event.ConnectListener;
import event.ConnectionEvent;
import event.DisconnectListener;
import protocol.Protocol;
import rpg.element.Element;
import rpg.element.Player;
import rpg.element.entity.FireMage;
import rpg.element.entity.Race;
import rpg.graphics.Drawer;
import rpg.graphics.MultiAbilityDrawer;
import rpg.graphics.ShowInventory;
import rpg.graphics.Translate;
import rpg.logic.Tuple;
import rpg.logic.level.Game;
import rpg.logic.level.Level2;
import rpg.ui.ServerStation;
import tcp.Switchboard;
import tcp.TcpClient;
import tcp.message.Message;

public class GameServer {

	public static void main(String[] args) throws IOException {
		Game game = new Level2();
		new GameServer(game).start();
		SwingUtilities.invokeLater(() -> new ServerStation(game).start());
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
				game.addPlayer(c, Race.HUMAN, new FireMage());
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

	public void start() {
		server.start();
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
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				send();
			}
		}, 0, 30);
	}

}
