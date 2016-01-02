package rpg.network;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import event.ConnectListener;
import event.ConnectionEvent;
import event.MessageEvent;
import event.MessageListener;
import protocol.XMLProtocol;
import rpg.element.Element;
import rpg.logic.Game;
import rpg.logic.Level;
import rpg.logic.Level2;
import rpg.ui.ServerStation;
import tcp.chat.ChatServer;
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
	private XMLProtocol protocol;
	private Transformer builder;

	public GameServer(Game game) throws IOException {
		received = new CopyOnWriteArrayList<>();
		timer = new Timer();
		inner = new ChatServer();
		protocol = new XMLProtocol();
		inner.addMessageListener(new MessageListener() {
			@Override
			public void onReceive(MessageEvent e) {
				received.add(new NetworkCommand(e.getMessage().getData()));
			}
		});
		try {
			builder = TransformerFactory.newInstance().newTransformer();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
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
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				inner.send(Message.data(Source.SERVER, "start"));
				for (Element e : game.getLevel().getDynamicElements()) {
					StringWriter sw = new StringWriter();
					try {
						builder.transform(new DOMSource(protocol.encode(e)), new StreamResult(sw));
					} catch (TransformerException e1) {
						e1.printStackTrace();
					}
					inner.send(Message.data(Source.SERVER, "xml-element " + sw.toString()));
				}
				inner.send(Message.data(Source.SERVER, "end"));
			}
		}, 0, 10);
	}

	public boolean isAllowed(NetworkCommand command) {
		return true;
	}

	public static void main(String[] args) {
		Level level = new Level2();
		Game game = new Game(level);
		try {
			GameServer server = new GameServer(game);
			server.inner.addConnectListener(new ConnectListener() {

				@Override
				public void onConnect(ConnectionEvent e) {
					server.inner.send(Message.create(Source.SERVER, Target.BROADCAST, Type.METADATA,
							"your number is " + server.counter++ + ""));
				}
			});
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ServerStation gs = new ServerStation(game);
		gs.start();

	}

}
