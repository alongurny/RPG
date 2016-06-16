package network;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import network.event.ConnectListener;
import network.event.ConnectionEvent;
import network.event.DisconnectListener;
import network.event.MessageListener;
import network.message.Message;
import network.message.Message.Type;

/**
 * This class is a {@link TcpServer} that allows the caller to use three kinds
 * of listeners:
 * <ul>
 * <li>Connect listeners: fired when a client connects.</li>
 * <li>Disconnect listeners: fired called when a client disconnects.</li>
 * <li>Message listeners: fired when a message is received from a client.</li>
 * </ul>
 * 
 * @author Alon
 *
 */
public class Switchboard extends TcpServer {

	private List<TcpClient> clients;
	private List<MessageListener> messageListeners;
	private List<ConnectListener> connectListeners;
	private List<DisconnectListener> disconnectListeners;

	/**
	 * Constructs a new <code>Switchboard</code> at the given port.
	 * 
	 * @param port
	 *            the port
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public Switchboard(int port) throws IOException {
		super(port);
		clients = new CopyOnWriteArrayList<>();
		messageListeners = new ArrayList<>();
		connectListeners = new ArrayList<>();
		disconnectListeners = new ArrayList<>();
	}

	/**
	 * Add a new connect listener, which is fired when a client connects.
	 * 
	 * @param listener
	 *            a connect listener
	 */
	public void addConnectListener(ConnectListener listener) {
		connectListeners.add(listener);
	}

	/**
	 * Add a new disconnect listener, which is fired when a client disconnects.
	 * 
	 * @param listener
	 *            a disconnect listener
	 */
	public void addDisconnectListener(DisconnectListener listener) {
		disconnectListeners.add(listener);
	}

	/**
	 * Add a new message listener, which is fired when a message is received.
	 * 
	 * @param listener
	 *            a message listener
	 */
	public void addMessageListener(MessageListener listener) {
		messageListeners.add(listener);
	}

	public void forEach(Consumer<TcpClient> action) {
		clients.forEach(action);
	}

	/**
	 * Removes this listener.
	 * 
	 * @param listener
	 *            a connect listener.
	 */
	public void removeConnectListener(ConnectListener listener) {
		connectListeners.remove(listener);
	}

	/**
	 * Removes this listener.
	 * 
	 * @param listener
	 *            a disconnect listener.
	 */
	public void removeDisconnectListener(DisconnectListener listener) {
		disconnectListeners.remove(listener);
	}

	/**
	 * Removes this listener.
	 * 
	 * @param listener
	 *            a message listener.
	 */
	public void removeMessageListener(MessageListener listener) {
		messageListeners.remove(listener);
	}

	/**
	 * Send a message to all connected client.
	 * 
	 * @param message
	 *            the message to send
	 */
	public void send(Message message) {
		clients.forEach(client -> client.send(message));
	}

	@Override
	protected void handleSocket(Socket s) throws IOException {
		System.out.println("Handling client " + s.getInetAddress());
		TcpClient client = new TcpClient(s);
		clients.add(client);
		connectListeners.forEach(cl -> cl.onConnect(new ConnectionEvent(client)));
		client.addMessageListener(e -> {
			handleMessage(client, e.getMessage());
			messageListeners.forEach(ml -> ml.onReceive(e));
		});
		client.addDisconnectListener(e -> {
			System.out.println("End of client " + s.getInetAddress());
			disconnectListeners.forEach(cl -> cl.onDisconnect(e));
			clients.remove(client);
			try {
				client.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		client.listen();
	}

	private void handleMessage(TcpClient client, Message message) {
		String data = message.getData();
		if (message.getType() == Type.METADATA && data.equals("disconnect")) {
			client.stop();
			clients.remove(client);
		}
	}
}
