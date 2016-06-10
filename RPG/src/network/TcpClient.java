package network;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import network.event.ConnectionEvent;
import network.event.DisconnectListener;
import network.event.MessageEvent;
import network.event.MessageListener;
import network.message.Message;
import network.message.MessageDeliveryProtocol;
import network.protocol.TwoWayProtocol;

/**
 * A TCP client.
 * 
 * @author Alon
 *
 */
public class TcpClient implements Closeable {

	public enum State {
		NOT_LISTENING, LISTENING
	}

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private TwoWayProtocol<Message, String> twoWayProtocol = new MessageDeliveryProtocol();
	private List<MessageListener> messageListeners;
	private List<DisconnectListener> disconnectListeners;
	private State state;

	/**
	 * Constructs a new client wrapping the given socket.
	 * 
	 * @param socket
	 *            a socket
	 * @throws IOException
	 */
	public TcpClient(Socket socket) throws IOException {
		this.socket = socket;
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(socket.getOutputStream(), true);
		this.messageListeners = new ArrayList<>();
		this.disconnectListeners = new ArrayList<>();
		this.state = State.NOT_LISTENING;
	}

	/**
	 * Constructs a new {@link Socket} with <code>host</code> and
	 * <code>port</code> and then calls {@link #TcpClient(Socket)}.
	 * 
	 * @param host
	 *            the server's host
	 * @param port
	 *            the server's port
	 * @throws IOException
	 */
	public TcpClient(InetAddress host, int port) throws IOException {
		this(new Socket(host, port));
	}

	/**
	 * Constructs a new {@link Socket} with <code>host</code> and
	 * <code>port</code> and then calls {@link #TcpClient(Socket)}.
	 * 
	 * @param host
	 *            the server's host
	 * @param port
	 *            the server's port
	 * @throws IOException
	 */
	public TcpClient(String host, int port) throws IOException {
		this(new Socket(host, port));
	}

	/**
	 * Add a new disconnect listener, which is fired when a client disconnects.
	 * 
	 * @param listener
	 *            a disconnect listener
	 */
	public void addDisconnectListener(DisconnectListener listener) {
		verifyNotListening();
		disconnectListeners.add(listener);
	}

	/**
	 * Add a new message listener, which is fired when a message is received.
	 * 
	 * @param listener
	 *            a message listener
	 */
	public void addMessageListener(MessageListener listener) {
		verifyNotListening();
		messageListeners.add(listener);
	}

	/**
	 * Closes the wrapped socket.
	 */
	public void close() throws IOException {
		socket.close();
	}

	/**
	 * Returns the IP address of the wrapped socket.
	 * 
	 * @return the IP address of the wrapped socket
	 */
	public InetAddress getInetAddress() {
		return socket.getInetAddress();
	}

	/**
	 * Invocation of this method will cause this client to start receiving
	 * messages from the server.
	 */
	public void listen() {
		verifyNotListening();
		state = State.LISTENING;
		new Thread(() -> {
			Optional<Message> message = receive();
			while (message.isPresent() && state == State.LISTENING) {
				for (MessageListener listener : messageListeners) {
					listener.onReceive(new MessageEvent(this, message.get()));
				}
				message = receive();
			}
			disconnectListeners.forEach(cl -> cl.onDisconnect(new ConnectionEvent(this)));
			state = State.NOT_LISTENING;
		}, Thread.currentThread().getName() + "/Client Listen Thread").start();
	}

	/**
	 * Send a message to the server.
	 * 
	 * @param message
	 *            the message to send
	 */
	public void send(Message message) {
		out.println(twoWayProtocol.encode(message));
	}

	/**
	 * Stops this client. It will no longer receive messages from the server.
	 */
	public void stop() {
		verifyNotListening();
		state = State.NOT_LISTENING;
	}

	private Optional<Message> receive() {
		Message res = null;
		try {
			String line = in.readLine();
			res = line != null ? twoWayProtocol.decode(line) : null;
		} catch (IOException e) {
		}
		return Optional.ofNullable(res);
	}

	/**
	 * Removes a message listener that was added by
	 * {@link #addMessageListener(MessageListener) addMessageListener}. If the
	 * listener does not exist, does nothing.
	 * 
	 * @param messageListener
	 */
	public void removeMessageListener(MessageListener messageListener) {
		verifyNotListening();
		messageListeners.remove(messageListener);
	}

	private void verifyNotListening() {
		if (state != State.NOT_LISTENING) {
			throw new IllegalStateException("Listening already");
		}
	}

}
