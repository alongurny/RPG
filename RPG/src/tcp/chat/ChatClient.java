package tcp.chat;

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
import tcp.chat.message.Message;
import tcp.chat.message.MessageDeliveryProtocol;

public class ChatClient implements Closeable {

	public static final int APPLICATION_PORT = 1234;

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private Protocol<Message, String> protocol = new MessageDeliveryProtocol();
	private List<MessageListener> messageListeners;
	private List<DisconnectListener> disconnectListeners;
	private String name;
	private boolean listening;

	public ChatClient(Socket socket, boolean autoListen) throws IOException {
		this.socket = socket;
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(socket.getOutputStream(), true);
		this.messageListeners = new ArrayList<>();
		this.disconnectListeners = new ArrayList<>();
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
			disconnectListeners.forEach(cl -> cl.onDisconnect(new ConnectionEvent()));
		} , Thread.currentThread().getName() + "/Client Listen Thread").start();
	}

	public void addMessageListener(MessageListener listener) {
		messageListeners.add(listener);
	}

	public void addConnectionListener(DisconnectListener listener) {
		disconnectListeners.add(listener);
	}

	public void removeMessageListener(MessageListener listener) {
		messageListeners.remove(listener);
	}

	public void removeConnectionListener(DisconnectListener listener) {
		disconnectListeners.remove(listener);
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
