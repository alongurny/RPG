package rpg.network;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import event.MessageEvent;
import event.MessageListener;
import protocol.ThingToStringProtocol;
import rpg.element.Element;
import rpg.geometry.Vector2D;
import rpg.logic.Game;
import rpg.logic.level.Level;
import rpg.logic.level.Level2;
import rpg.ui.GameStation;
import rpg.ui.KeyTracker;
import rpg.ui.MultiKeyEvent;
import rpg.ui.MultiKeyListener;
import tcp.chat.ChatClient;
import tcp.chat.message.Message;
import tcp.chat.message.Message.Source;
import tcp.chat.message.Message.Type;

public class GameClient implements KeyListener, MultiKeyListener {

	private Game game;
	private List<NetworkCommand> commands;
	private ChatClient chatClient;
	private List<Element> elements;
	private ThingToStringProtocol protocol;
	private int num = -1;

	public GameClient(Game game, Socket toServer) {
		this.game = game;
		commands = new CopyOnWriteArrayList<>();
		protocol = new ThingToStringProtocol();
		elements = new CopyOnWriteArrayList<>();

		try {
			chatClient = new ChatClient(toServer);
			chatClient.addMessageListener(new MessageListener() {
				@Override
				public void onReceive(MessageEvent e) {
					String data = e.getMessage().getData();
					if (e.getMessage().getType() == Type.DATA) {
						if (data.equals("start")) {
						} else if (data.equals("end")) {
							game.getLevel().replaceDynamicElements(new ArrayList<>(elements));
							elements.clear();
						} else if (data != null && data.startsWith("xml-element ")) {
							data = data.substring("xml-element ".length());
							elements.add((Element) protocol.decode(data));
						}
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendCommands() {
		for (NetworkCommand c : commands) {
			chatClient.send(Message.data(Source.SERVER, c.toString()));
		}
		commands.clear();
	}

	@Override
	public void keysChange(MultiKeyEvent e) {
		Vector2D velocity = Vector2D.ZERO;
		if (e.get(KeyEvent.VK_UP) || e.get(KeyEvent.VK_DOWN)) {
			if (e.get(KeyEvent.VK_UP)) {
				velocity = velocity.add(Vector2D.NORTH);
			}
			if (e.get(KeyEvent.VK_DOWN)) {
				velocity = velocity.add(Vector2D.SOUTH);
			}
		}
		if (e.get(KeyEvent.VK_LEFT) || e.get(KeyEvent.VK_RIGHT)) {
			if (e.get(KeyEvent.VK_LEFT)) {
				velocity = velocity.add(Vector2D.WEST);
			}
			if (e.get(KeyEvent.VK_RIGHT)) {
				velocity = velocity.add(Vector2D.EAST);
			}
		}
		commands.add(new NetworkCommand("player " + num + " setVector velocityDirection " + velocity));
		if (!velocity.equals(Vector2D.ZERO)) {
			commands.add(new NetworkCommand("player " + num + " setVector direction " + velocity));
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			commands.add(new NetworkCommand(String.format("player %s interact", num)));
		} else if (e.getKeyCode() - '0' >= 1
				&& e.getKeyCode() - '0' <= game.getLevel().getPlayer(num).getAbilityCount()) {
			commands.add(new NetworkCommand(String.format("player %s cast %s", num, e.getKeyCode() - '1')));
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public static void main(String[] args) {
		Level level = new Level2();
		Game game = new Game(level);
		try {
			Socket s = new Socket("localhost", 1234);

			GameClient client = new GameClient(game, s);
			client.chatClient.addMessageListener(new MessageListener() {

				@Override
				public void onReceive(MessageEvent e) {
					if (e.getMessage().getType() == Type.METADATA && client.num == -1) {
						String data = e.getMessage().getData();
						if (data.startsWith("your number is ")) {
							client.num = Integer.parseInt(data.replace("your number is ", ""));
							GameStation gs = new GameStation(game, client.num) {
								@Override
								public void run() {
									client.sendCommands();
								}
							};
							KeyTracker keyTracker = new KeyTracker();
							keyTracker.addMultiKeyListener(client);
							gs.addKeyListener(keyTracker);
							gs.addKeyListener(client);
							gs.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseClicked(MouseEvent e) {
									Point offset = gs.getBoard().getPanel().getOffset();
									client.setTarget(e.getX() + offset.x, e.getY() + offset.y);
								}
							});
							gs.start();
						}
					}
				}
			});
			client.chatClient.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void setTarget(int x, int y) {
		for (Element element : game.getLevel().getDynamicElements()) {
			if (element.getAbsoluteRect().contains(new Vector2D(x, y))) {

				commands.add(
						new NetworkCommand(String.format("player %s setTarget %s", num, element.getInteger("id"))));
				return;
			}
		}
		commands.add(new NetworkCommand(String.format("player %s setTarget null", num)));
	}
}
