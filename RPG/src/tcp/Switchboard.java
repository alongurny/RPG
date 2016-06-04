package tcp;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import event.ConnectListener;
import event.ConnectionEvent;
import event.DisconnectListener;
import event.MessageListener;
import tcp.message.Message;

public class Switchboard extends TcpServer {

	public static final int APPLICATION_PORT = 1234;

	private List<TcpClient> clients;
	private List<MessageListener> messageListeners;
	private List<ConnectListener> connectListeners;
	private List<DisconnectListener> disconnectListeners;

	public Switchboard() throws IOException {
		super(APPLICATION_PORT);
		clients = new CopyOnWriteArrayList<>();
		messageListeners = new ArrayList<>();
		connectListeners = new ArrayList<>();
		disconnectListeners = new ArrayList<>();
	}

	public void addConnectListener(ConnectListener listener) {
		connectListeners.add(listener);
	}

	public void addDisconnectListener(DisconnectListener listener) {
		disconnectListeners.add(listener);
	}

	public void addMessageListener(MessageListener listener) {
		messageListeners.add(listener);
	}

	public void forEach(Consumer<TcpClient> action) {
		clients.forEach(action);
	}

	private void handleMessage(TcpClient client, Message message) {
		String data = message.getData();
		switch (message.getType()) {
		case DATA:
			break;
		case METADATA:
			if (data.equals("disconnect")) {
				client.stop();
				clients.remove(client);
			}
			break;
		}
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
		client.addConnectionListener(e -> {
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

	public void removeDisconnectListener(DisconnectListener listener) {
		disconnectListeners.remove(listener);
	}

	public void removeMessageListener(MessageListener listener) {
		messageListeners.remove(listener);
	}

	public void send(Message message) {
		clients.forEach(client -> client.send(message));
	}
}