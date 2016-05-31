package rpg.network;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import event.ConnectListener;
import event.ConnectionEvent;
import event.MessageEvent;
import event.MessageListener;
import rpg.element.Element;
import rpg.graphics.draw.Drawer;
import rpg.logic.Game;
import rpg.logic.level.Level;
import rpg.logic.level.Level2;
import rpg.ui.ServerStation;
import tcp.ChatServer;
import tcp.message.Message;

public class GameServer {

	private List<NetworkCommand> received;
	private ChatServer server;
	private Timer timer;
	private Game game;
	private boolean firstConnection = false;
	private static int num = 0;

	public GameServer(Game game) throws IOException {
		received = new CopyOnWriteArrayList<>();
		timer = new Timer();
		server = new ChatServer();
		server.addConnectListener(new ConnectListener() {
			@Override
			public void onConnect(ConnectionEvent e) {
				firstConnection = true;
			}
		});
		server.addMessageListener(new MessageListener() {
			@Override
			public void onReceive(MessageEvent e) {
				received.add(new NetworkCommand(e.getMessage().getData()));
			}
		});
		this.game = game;
	}

	public void start() {
		server.start();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				for (NetworkCommand c : received) {
					if (isAllowed(c)) {
						c.execute(game.getLevel());
					}
				}
				received.clear();
			}
		}, 0, 30);
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				send();
			}
		}, 0, 30);
	}

	private void send() {
		server.send(Message.data("start"));
		for (Element e : game.getLevel().getDynamicElements()) {
			String t1 = Drawer.translate((int) e.getLocation().getX(), (int) e.getLocation().getY()).represent();
			String t2 = Drawer.translate((int) -e.getLocation().getX(), (int) -e.getLocation().getY()).represent();
			String[] str = e.getDrawer().represent().split("\n");
			server.send(Message.data("dynamic " + t1));
			for (String s : str) {
				server.send(Message.data("dynamic " + s));
			}
			server.send(Message.data("dynamic " + t2));
		}
		if (firstConnection) {
			server.send(Message.metadata("number " + num++));
			for (Element e : game.getLevel().getStaticElements()) {
				String[] str = e.getDrawer().represent().split("\n");
				String t1 = Drawer.translate((int) e.getLocation().getX(), (int) e.getLocation().getY()).represent();
				String t2 = Drawer.translate((int) -e.getLocation().getX(), (int) -e.getLocation().getY()).represent();
				server.send(Message.data("static " + t1));
				for (String s : str) {
					server.send(Message.data("static " + s));
				}
				server.send(Message.data("static " + t2));
			}
			firstConnection = false;
		}
		server.send(Message.data("end"));
	}

	public boolean isAllowed(NetworkCommand command) {
		if (command.toString().startsWith("nothing")) {
			return true;
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		Level level = new Level2();
		Game game = new Game(level);
		new ServerStation(game).start();
		new GameServer(game).start();
	}

}
