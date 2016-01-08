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
import tcp.chat.message.Message.Source;
import tcp.chat.message.Message.Target;
import tcp.chat.message.Message.Type;

public class GameServer {

	private List<NetworkCommand> received;
	private ChatServer server;
	private Timer timer;
	private Game game;
	private int counter = 0;
	private ThingToStringProtocol protocol;

	public GameServer(Game game) throws IOException {
		received = new CopyOnWriteArrayList<>();
		timer = new Timer();
		server = new ChatServer();
		protocol = new ThingToStringProtocol();
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
					c.execute(game.getLevel());
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
		server.send(Message.data(Source.SERVER, "start"));
		for (Element e : game.getLevel().getDynamicElements()) {
			server.send(Message.data(Source.SERVER, "xml-element " + protocol.encode(e)));
		}
		server.send(Message.data(Source.SERVER, "end"));
	}

	public boolean isAllowed(NetworkCommand command) {
		return true;
	}

	public static void main(String[] args) {
		Level level = new Level2();
		Game game = new Game(level);
		try {
			GameServer server = new GameServer(game);
			server.server.addConnectListener(new ConnectListener() {

				@Override
				public void onConnect(ConnectionEvent e) {
					server.server.send(Message.create(Source.SERVER, Target.BROADCAST, Type.METADATA,
							"your number is " + server.counter++ + ""));
				}
			});
			server.start();
			ServerStation gs = new ServerStation(game) {
				@Override
				public void doSomething() {
				}
			};
			gs.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
