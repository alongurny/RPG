package rpg.network;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import event.MessageEvent;
import event.MessageListener;
import protocol.ThingToStringProtocol;
import rpg.element.Element;
import rpg.geometry.Vector2D;
import rpg.graphics.draw.Drawer;
import rpg.ui.GamePanel;
import rpg.ui.GameStation;
import rpg.ui.MultiKeyEvent;
import rpg.ui.MultiKeyListener;
import tcp.chat.ChatClient;
import tcp.chat.message.Message;
import tcp.chat.message.Message.Source;
import tcp.chat.message.Message.Type;

public class GameClient implements KeyListener, MultiKeyListener {

	private List<NetworkCommand> commands;
	private ChatClient chatClient;
	private ThingToStringProtocol protocol;

	public GameClient(GamePanel panel, Socket toServer) throws IOException {
		commands = new CopyOnWriteArrayList<>();
		protocol = new ThingToStringProtocol();
		chatClient = new ChatClient(toServer);
		chatClient.addMessageListener(new MessageListener() {
			@Override
			public void onReceive(MessageEvent e) {

				if (e.getMessage().getSource() == Source.SERVER && e.getMessage().getType() == Type.DATA) {
					String data = e.getMessage().getData();
					if (data.equals("start")) {
					} else if (data.equals("end")) {
						panel.flush();
					} else if (data.startsWith("dynamic ")) {
						Element element = (Element) protocol.decode(data.substring(8));
						Drawer drawer = element.getDrawer();
						drawer.set("location", element.getLocation());
						panel.addDrawer(drawer);
					} else if (data.startsWith("static ")) {
						Element element = (Element) protocol.decode(data.substring(7));
						Drawer drawer = element.getDrawer();
						drawer.set("location", element.getLocation());
						panel.addStaticDrawer(drawer);
					}
				}
			}
		});
		chatClient.listen();
	}

	public void sendCommands() {
		for (NetworkCommand c : commands) {
			chatClient.send(Message.data(Source.valueOf("FRIEND A"), c.toString()));
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
		commands.add(new NetworkCommand("player " + 0 + " setVector velocityDirection " + velocity));
		if (!velocity.equals(Vector2D.ZERO)) {
			commands.add(new NetworkCommand("player " + 0 + " setVector direction " + velocity));
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			commands.add(new NetworkCommand(String.format("player %s interact", 0)));
		} else if (e.getKeyCode() - '0' >= 1 && e.getKeyCode() - '0' <= 9) {
			commands.add(new NetworkCommand(String.format("player %s cast %s", 0, e.getKeyCode() - '1')));
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public static void main(String[] args) {
		try {
			new GameStation(new Socket("localhost", 1234)).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
