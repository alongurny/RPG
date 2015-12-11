package rpg.network.tcp.chat;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import rpg.network.event.ConnectionEvent;
import rpg.network.event.ConnectionListener;
import rpg.network.event.MessageEvent;
import rpg.network.event.MessageListener;
import rpg.network.protocol.Protocol;
import rpg.network.tcp.chat.message.Message;
import rpg.network.tcp.chat.message.MessageDeliveryProtocol;

public class ChatClient implements Closeable {

	public static final int APPLICATION_PORT = 1234;

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private Protocol<Message, String> protocol = new MessageDeliveryProtocol();
	private List<MessageListener> messageListeners;
	private List<ConnectionListener> connectionListeners;
	private String name;
	private boolean listening;

	public ChatClient(Socket socket, boolean autoListen) throws IOException {
		this.socket = socket;
		this.in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		this.out = new PrintWriter(socket.getOutputStream(), true);
		this.messageListeners = new ArrayList<>();
		this.connectionListeners = new ArrayList<>();
		if (autoListen) {
			listen();
		}
	}

	public ChatClient(InetAddress host, boolean autoListen) throws IOException {
		this(new Socket(host, APPLICATION_PORT), autoListen);
	}

	public ChatClient(String host, boolean autoListen) throws IOException {
		this(new Socket(host, APPLICATION_PORT), autoListen);
	}

	public InetAddress getInetAddress() {
		return socket.getInetAddress();
	}

	public void listen() {
		if (listening) {
			throw new IllegalStateException("Already listening");
		}
		listening = true;
		new Thread(() -> {
			Message m;
			do {
				m = receive();
				if (m == null) {
					break;
				}
				for (MessageListener ml : messageListeners) {
					ml.onReceive(new MessageEvent(m));
				}
			} while (listening);
			connectionListeners.forEach(cl -> cl.onEnd(new ConnectionEvent()));
		}, Thread.currentThread().getName() + "/Client Listen Thread").start();
	}

	public void addMessageListener(MessageListener listener) {
		messageListeners.add(listener);
	}

	public void addConnectionListener(ConnectionListener listener) {
		connectionListeners.add(listener);
	}

	public void removeMessageListener(MessageListener listener) {
		messageListeners.remove(listener);
	}

	public void removeConnectionListener(ConnectionListener listener) {
		connectionListeners.remove(listener);
	}

	public void send(Message m) {
		out.println(protocol.encode(m));
	}

	private Message receive() {
		Message res = null;
		try {
			String line = in.readLine();
			res = line != null ? protocol.decode(line) : null;
		} catch (IOException e) {
			e.printStackTrace();
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
		listening = false;
	}

	public void close() throws IOException {
		socket.close();
	}

}
