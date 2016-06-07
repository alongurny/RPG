package rpg.network;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.SwingUtilities;

import event.MessageEvent;
import event.MessageListener;
import protocol.Protocol;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.ui.GamePanel;
import rpg.ui.GameStation;
import tcp.TcpClient;
import tcp.message.Message;

public class GameClient {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				new GameStation(new Socket("localhost", 1234)).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private TcpClient tcpClient;
	private List<String> commands;
	private Protocol<Drawer, String> protocol;
	private GamePanel panel;

	private boolean showInventory;

	public GameClient(GamePanel panel, Socket toServer) throws IOException {
		commands = new CopyOnWriteArrayList<>();
		tcpClient = new TcpClient(toServer);
		protocol = new DrawerProtocol();
		this.panel = panel;
		tcpClient.addMessageListener(new MessageListener() {
			@Override
			public void onReceive(MessageEvent e) {
				String data = e.getMessage().getData();
				if (data.equals("start")) {
				} else if (data.equals("end")) {
					panel.flush();
				} else if (data.startsWith("dynamic ")) {
					Drawer drawer = protocol.decode(data.substring("dynamic ".length()));
					panel.addDrawer(drawer);
				} else if (data.startsWith("static ")) {
					Drawer drawer = protocol.decode(data.substring("static ".length()));
					panel.addStaticDrawer(drawer);
				} else if (data.startsWith("location ")) {
					panel.setOffsetByPlayerLocation(Vector2D.valueOf(data.substring("location ".length())));
				} else if (data.startsWith("absolute ")) {
					Drawer drawer = protocol.decode(data.substring("absolute ".length()));
					panel.addAbsoluteDrawer(drawer);
				} else if (data.startsWith("inventory ") && showInventory) {
					Drawer drawer = protocol.decode(data.substring("inventory ".length()));
					panel.addAbsoluteDrawer(drawer);
				} else if (data.startsWith("dimensions ")) {
					panel.setDimensions(Vector2D.valueOf(data.substring("dimensions ".length())));
				}
			}
		});
		tcpClient.listen();
	}

	public void addCommand(String command) {
		commands.add(command);
	}

	public GamePanel getPanel() {
		return panel;
	}

	public void sendCommands() {
		for (String command : commands) {
			tcpClient.send(Message.data(command));
		}
		commands.clear();
	}

	public void setShowInventory(boolean showInventory) {
		this.showInventory = showInventory;
	}

}
