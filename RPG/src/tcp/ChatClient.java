package tcp;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import event.ConnectionEvent;
import event.DisconnectListener;
import event.MessageEvent;
import event.MessageListener;
import protocol.Protocol;
import tcp.message.Message;
import tcp.message.MessageDeliveryProtocol;

public class ChatClient implements Closeable {

	public enum State {
		NOT_LISTENING, LISTENING, CLOSED
	}

	public static final int APPLICATION_PORT = 1234;

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private Protocol<Message, String> protocol = new MessageDeliveryProtocol();
	private List<MessageListener> messageListeners;
	private List<DisconnectListener> disconnectListeners;
	private String name;
	private State state;

	public ChatClient(Socket socket) throws IOException {
		this.socket = socket;
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(socket.getOutputStream(), true);
		this.messageListeners = new ArrayList<>();
		this.disconnectListeners = new ArrayList<>();
		this.state = State.NOT_LISTENING;
	}

	public ChatClient(InetAddress host) throws IOException {
		this(new Socket(host, APPLICATION_PORT));
	}

	public ChatClient(String host) throws IOException {
		this(new Socket(host, APPLICATION_PORT));
	}

	public InetAddress getInetAddress() {
		return socket.getInetAddress();
	}

	public void listen() {
		if (state != State.NOT_LISTENING) {
			throw new IllegalStateException("Already started");
		}
		state = State.LISTENING;
		new Thread(() -> {
			Message message;
			do {
				message = receive();
				if (message == null) {
					break;
				}
				for (MessageListener listener : messageListeners) {
					listener.onReceive(new MessageEvent(message));
				}
			} while (state == State.LISTENING);
			disconnectListeners.forEach(cl -> cl.onDisconnect(new ConnectionEvent()));
		} , Thread.currentThread().getName() + "/Client Listen Thread").start();
	}

	public void addMessageListener(MessageListener listener) {
		if (state != State.NOT_LISTENING) {
			throw new IllegalStateException("Client is already listening");
		}
		messageListeners.add(listener);
	}

	public void addConnectionListener(DisconnectListener listener) {
		if (state != State.NOT_LISTENING) {
			throw new IllegalStateException("Client is already listening");
		}
		disconnectListeners.add(listener);
	}

	public void removeMessageListener(MessageListener listener) {
		if (state != State.NOT_LISTENING) {
			throw new IllegalStateException("Client is already listening");
		}
		messageListeners.remove(listener);
	}

	public void removeConnectionListener(DisconnectListener listener) {
		if (state != State.NOT_LISTENING) {
			throw new IllegalStateException("Client is already listening");
		}
		disconnectListeners.remove(listener);
	}

	public void send(Message message) {
		out.println(protocol.encode(message));
	}

	private Message receive() {
		Message res = null;
		try {
			String line = in.readLine();
			res = line != null ? protocol.decode(line) : null;
		} catch (IOException e) {
		}
		return res;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void stop() {
		if (state != State.LISTENING) {
			throw new IllegalStateException("Not started");
		}
		state = State.CLOSED;
	}

	public void close() throws IOException {
		socket.close();
	}

}
