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
import protocol.ThingToStringProtocol;
import rpg.element.Element;
import rpg.logic.Game;
import rpg.logic.level.Level;
import rpg.logic.level.Level2;
import rpg.ui.ServerStation;
import tcp.chat.ChatServer;
import tcp.chat.message.Message;

public class GameServer {

	private List<NetworkCommand> received;
	private ChatServer server;
	private Timer timer;
	private Game game;
	private ThingToStringProtocol protocol;
	private boolean sendStatics = false;

	public GameServer(Game game) throws IOException {
		received = new CopyOnWriteArrayList<>();
		timer = new Timer();
		server = new ChatServer();
		protocol = new ThingToStringProtocol();
		server.addConnectListener(new ConnectListener() {
			@Override
			public void onConnect(ConnectionEvent e) {
				sendStatics = true;
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
			server.send(Message.data("dynamic " + protocol.encode(e)));
		}
		if (sendStatics) {
			for (Element e : game.getLevel().getStaticElements()) {
				server.send(Message.data("static " + protocol.encode(e)));
			}
			sendStatics = false;
		}
		server.send(Message.data("end"));
	}

	public boolean isAllowed(NetworkCommand command) {
		return true;
	}

	public static void main(String[] args) throws IOException {
		Level level = new Level2();
		Game game = new Game(level);
		new GameServer(game).start();
		new ServerStation(game).start();
	}

}
