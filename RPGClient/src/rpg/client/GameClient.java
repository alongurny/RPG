package rpg.client;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import network.TcpClient;
import network.event.MessageEvent;
import network.event.MessageListener;
import network.message.Message;
import network.protocol.Protocol;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.DrawerProtocol;

/**
 * This class represents a client of the game. It wraps {@link TcpClient} and
 * has many methods to get and receive information from the server.
 * 
 * @author Alon
 *
 */
public class GameClient {

	private TcpClient tcpClient;
	private List<String> commands;
	private Protocol<Drawer, String> protocol;
	private GamePanel panel;
	private boolean showInventory;

	/**
	 * Constructs a new client, using a game panel, a socket and a profession.
	 * 
	 * @param panel
	 *            the panel that will draw the game for this client
	 * @param toServer
	 *            the socket which this client connects through
	 * @param profession
	 *            a string representation of the player's profession
	 * @throws IOException
	 *             if an I/O exception occurs
	 */
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

	/**
	 * Add a new string command to this client. Commands are not sent
	 * immediately to the server, but rather, are sent together by the
	 * {@link #sendCommands() sendCommand} method.
	 * 
	 * @param command
	 *            a command to send to the server
	 */
	public void addCommand(String command) {
		commands.add(command);
	}

	/**
	 * Returns the game panel that draws the game for this client
	 * 
	 * @return the game panel that draws the game for this client
	 */
	public GamePanel getPanel() {
		return panel;
	}

	/**
	 * Sends all the commands that were added using {@link #addCommand(String)
	 * addCommand} to the server.
	 */
	public void sendCommands() {
		for (String command : commands) {
			tcpClient.send(Message.normal(command));
		}
		commands.clear();
	}

	/**
	 * Sets the showInventory field: whether inventory should be shown or not.
	 * 
	 * @param showInventory
	 *            <code>true</code> if inventory should be shown,
	 *            <code>false</code> otherwise
	 */
	public void setShowInventory(boolean showInventory) {
		this.showInventory = showInventory;
	}

}
