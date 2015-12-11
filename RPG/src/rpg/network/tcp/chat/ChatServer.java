package rpg.network.tcp.chat;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import rpg.network.event.ConnectionListener;
import rpg.network.event.MessageListener;
import rpg.network.tcp.TcpServer;
import rpg.network.tcp.chat.message.Message;

public class ChatServer extends TcpServer {

	public static final int APPLICATION_PORT = 1234;

	private List<ChatClient> clients;
	private List<MessageListener> messageListeners;
	private List<ConnectionListener> endListeners;

	public ChatServer() throws IOException {
		super(APPLICATION_PORT);
		clients = new CopyOnWriteArrayList<>();
		messageListeners = new ArrayList<>();
		endListeners = new ArrayList<>();
	}

	public void addMessageListener(MessageListener listener) {
		messageListeners.add(listener);
	}

	public void addConnectionEndListener(ConnectionListener listener) {
		endListeners.add(listener);
	}

	public void removeMessageListener(MessageListener listener) {
		messageListeners.remove(listener);
	}

	public void removeConnectionEndListener(ConnectionListener listener) {
		endListeners.remove(listener);
	}

	@Override
	protected void handleSocket(Socket s) throws IOException {
		System.out.println("Handling client " + s.getInetAddress());
		ChatClient client = new ChatClient(s, false);
		clients.add(client);
		client.addMessageListener(e -> {
			handleMessage(client, e.getMessage());
			messageListeners.forEach(ml -> ml.onReceive(e));
		});
		client.addConnectionListener(e -> {
			System.out.println("End of client " + s.getInetAddress());
			endListeners.forEach(cl -> cl.onEnd(e));
			clients.remove(client);
			try {
				client.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});
		client.listen();
	}

	private void handleMessage(ChatClient client, Message message) {
		Message.Target target = message.getTarget();
		String data = message.getData();
		switch (message.getType()) {
		case DATA:
			if (Message.Target.isBroadcast(target)) {
				clients.forEach(c -> c.send(message));
			}
			if (Message.Target.isFriend(target)) {
				clients.forEach(c -> {
					if (c.getInetAddress().getHostAddress()
							.equals(target.getHostName())
							|| c.getInetAddress().getHostName()
									.equals(target.getHostName())) {
						c.send(message);
					}
				});
			}
			break;
		case METADATA:
			if (Message.Target.isServer(target)) {
				if (data.startsWith("connect ")) {
					String name = data.replace("connect ", "");
					client.setName(name);
				} else if (data.equals("disconnect")) {
					client.stop();
					clients.remove(client);
				}
			}
			break;
		}
	}
}
