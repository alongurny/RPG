package rpg.network;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.SwingUtilities;

import event.MessageEvent;
import event.MessageListener;
import protocol.Protocol;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.ui.GamePanel;
import rpg.ui.GameStation;
import tcp.ChatClient;
import tcp.message.Message;
import tcp.message.Message.Type;

public class GameClient {

	private ChatClient chatClient;
	private List<NetworkCommand> commands;
	private Protocol<Drawer, String> protocol;
	private Optional<Integer> num;
	private GamePanel panel;

	public GameClient(GamePanel panel, Socket toServer) throws IOException {
		commands = new CopyOnWriteArrayList<>();
		chatClient = new ChatClient(toServer);
		protocol = new DrawerProtocol();
		this.panel = panel;
		num = Optional.empty();
		chatClient.addMessageListener(new MessageListener() {
			@Override
			public void onReceive(MessageEvent e) {
				if (e.getMessage().getType() == Type.METADATA) {
					String data = e.getMessage().getData();
					if (data.startsWith("number ") && !num.isPresent()) {
						num = Optional.of(Integer.valueOf(data.replace("number ", "")));
					}
				}
			}
		});
		chatClient.addMessageListener(new MessageListener() {
			@Override
			public void onReceive(MessageEvent e) {
				String data = e.getMessage().getData();
				if (data.equals("start")) {
				} else if (data.equals("end")) {
					panel.flush();
				} else if (data.startsWith("dynamic ")) {
					Drawer drawer = protocol.decode(data.substring(8));
					panel.addDrawer(drawer);
				} else if (data.startsWith("static ")) {
					Drawer drawer = protocol.decode(data.substring(7));
					panel.addStaticDrawer(drawer);
				} else if (data.startsWith("location ")) {
					panel.setOffsetByPlayerLocation(Vector2D.valueOf(data.substring(9)));
				} else if (data.startsWith("absolute ")) {
					Drawer drawer = protocol.decode(data.substring(9));
					panel.addAbsoluteDrawer(drawer);
				}
			}
		});
		chatClient.listen();
	}

	public void addCommand(NetworkCommand command) {
		commands.add(command);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				new GameStation(new Socket("localhost", 1234)).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public int getNumber() {
		return num.get();
	}

	public void sendCommands() {
		for (NetworkCommand command : commands) {
			chatClient.send(Message.data(command.toString()));
		}
		commands.clear();
	}

	public GamePanel getPanel() {
		return panel;
	}

}
