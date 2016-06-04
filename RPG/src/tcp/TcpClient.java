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
import java.util.Optional;

import event.ConnectionEvent;
import event.DisconnectListener;
import event.MessageEvent;
import event.MessageListener;
import protocol.Protocol;
import tcp.message.Message;
import tcp.message.MessageDeliveryProtocol;

public class TcpClient implements Closeable {

	public enum State {
		NOT_LISTENING, LISTENING
	}

	public static final int APPLICATION_PORT = 1234;

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private Protocol<Message, String> protocol = new MessageDeliveryProtocol();
	private List<MessageListener> messageListeners;
	private List<DisconnectListener> disconnectListeners;
	private State state;

	public TcpClient(InetAddress host) throws IOException {
		this(new Socket(host, APPLICATION_PORT));
	}

	public TcpClient(Socket socket) throws IOException {
		this.socket = socket;
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(socket.getOutputStream(), true);
		this.messageListeners = new ArrayList<>();
		this.disconnectListeners = new ArrayList<>();
		this.state = State.NOT_LISTENING;
	}

	public TcpClient(String host) throws IOException {
		this(new Socket(host, APPLICATION_PORT));
	}

	public void addConnectionListener(DisconnectListener listener) {
		if (state != State.NOT_LISTENING) {
			throw new IllegalStateException("Client is already listening");
		}
		disconnectListeners.add(listener);
	}

	public void addMessageListener(MessageListener listener) {
		if (state != State.NOT_LISTENING) {
			throw new IllegalStateException("Client is already listening");
		}
		messageListeners.add(listener);
	}

	public void close() throws IOException {
		socket.close();
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

	private Optional<Message> receive() {
		Message res = null;
		try {
			String line = in.readLine();
			res = line != null ? protocol.decode(line) : null;
		} catch (IOException e) {
		}
		return Optional.ofNullable(res);
	}

	public void removeConnectionListener(DisconnectListener listener) {
		if (state != State.NOT_LISTENING) {
			throw new IllegalStateException("Client is already listening");
		}
		disconnectListeners.remove(listener);
	}

	public void removeMessageListener(MessageListener listener) {
		if (state != State.NOT_LISTENING) {
			throw new IllegalStateException("Client is already listening");
		}
		messageListeners.remove(listener);
	}

	public void send(Message message) {
		out.println(protocol.encode(message));
	}

	public void stop() {
		if (state != State.LISTENING) {
			throw new IllegalStateException("Not started");
		}
		state = State.NOT_LISTENING;
	}

}
