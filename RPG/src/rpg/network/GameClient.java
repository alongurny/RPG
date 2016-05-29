package rpg.network;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import event.MessageEvent;
import event.MessageListener;
import rpg.graphics.draw.Redrawable;
import rpg.ui.GamePanel;
import rpg.ui.GameStation;
import tcp.ChatClient;
import tcp.message.Message;
import tcp.message.Message.Type;

public class GameClient {

	private ChatClient chatClient;
	private List<NetworkCommand> commands;
	private Optional<Integer> num;
	private GamePanel panel;

	public GameClient(GamePanel panel, Socket toServer) throws IOException {
		commands = new CopyOnWriteArrayList<>();
		chatClient = new ChatClient(toServer);
		this.panel = panel;
		num = Optional.empty();
		chatClient.addMessageListener(new MessageListener() {
			@Override
			public void onReceive(MessageEvent e) {
				if (e.getMessage().getType() == Type.METADATA) {
					String data = e.getMessage().getData();
					if (data.startsWith("number ") && !num.isPresent()) {
						num = Optional.of(Integer.valueOf(data.replace("number ", "")));
					}
				}
			}
		});
		chatClient.addMessageListener(new MessageListener() {
			@Override
			public void onReceive(MessageEvent e) {
				String data = e.getMessage().getData();
				panel.addDrawable(Redrawable.get(data).getRedrawer());
			}
		});
		chatClient.listen();
	}

	public void addCommand(NetworkCommand command) {
		commands.add(command);
	}

	public static void main(String[] args) {
		try {
			new GameStation(new Socket("localhost", 1234)).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getNumber() {
		return num.get();
	}

	public void sendCommands() {
		for (NetworkCommand command : commands) {
			chatClient.send(Message.data(command.toString()));
		}
		commands.clear();
	}

	public GamePanel getPanel() {
		return panel;
	}

	private static double limit(double value, double min, double max) {
		return Math.min(max, Math.max(min, value));
	}
}
