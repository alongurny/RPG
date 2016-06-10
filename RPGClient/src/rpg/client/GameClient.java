package rpg.client;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import network.TcpClient;
import network.event.MessageEvent;
import network.event.MessageListener;
import network.message.Message;
import network.protocol.TwoWayProtocol;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.DrawerProtocol;

public class GameClient {

	private TcpClient tcpClient;
	private List<String> commands;
	private TwoWayProtocol<Drawer, String> protocol;
	private GamePanel panel;
	private boolean showInventory;

	public GameClient(GamePanel panel, Socket toServer, String profession) throws IOException {
		commands = new CopyOnWriteArrayList<>();
		tcpClient = new TcpClient(toServer);
		tcpClient.send(Message.normal("selection " + profession));
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
			tcpClient.send(Message.normal(command));
		}
		commands.clear();
	}

	public void setShowInventory(boolean showInventory) {
		this.showInventory = showInventory;
	}

}