package tcp.chat;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import event.ConnectListener;
import event.ConnectionEvent;
import event.MessageEvent;
import event.MessageListener;
import rpg.logic.Game;
import rpg.logic.Level;
import rpg.logic.Level1;
import rpg.network.NetworkCommand;
import rpg.ui.ServerStation;
import tcp.chat.message.Message;
import tcp.chat.message.Message.Source;
import tcp.chat.message.Message.Target;
import tcp.chat.message.Message.Type;

public class GameServer {

	private List<NetworkCommand> received;
	private ChatServer inner;
	private Timer timer;
	private Game game;
	private int counter = 0;

	public GameServer(Game game) throws IOException {
		received = new CopyOnWriteArrayList<>();
		timer = new Timer();
		inner = new ChatServer();
		inner.addMessageListener(new MessageListener() {
			@Override
			public void onReceive(MessageEvent e) {
				received.add(new NetworkCommand(e.getMessage().getData()));
			}
		});
		this.game = game;
	}

	public void start() {
		inner.start();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				for (NetworkCommand c : received) {
					c.execute(game);
				}
				received.clear();
			}
		}, 0, 10);
	}

	public boolean isAllowed(NetworkCommand command) {
		return true;
	}

	public static void main(String[] args) {
		Level level = new Level1();
		Game game = new Game(level);
		GameServer _server = null;
		try {
			_server = new GameServer(game);
		} catch (IOException e) {
			e.printStackTrace();
		}
		final GameServer server = _server;
		server.inner.addConnectListener(new ConnectListener() {

			@Override
			public void onConnect(ConnectionEvent e) {
				server.inner.send(Message.create(Source.SERVER, Target.BROADCAST, Type.METADATA,
						"your number is " + server.counter++ + ""));
			}
		});
		ServerStation gs = new ServerStation(game);
		gs.start();
		server.start();

	}

}
