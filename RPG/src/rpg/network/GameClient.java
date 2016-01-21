package rpg.network;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import event.MessageEvent;
import event.MessageListener;
import protocol.ThingToStringProtocol;
import rpg.element.Element;
import rpg.graphics.draw.Drawer;
import rpg.ui.GamePanel;
import rpg.ui.GameStation;
import tcp.chat.ChatClient;
import tcp.chat.message.Message;
import tcp.chat.message.Message.Source;
import tcp.chat.message.Message.Type;

public class GameClient {

	private ChatClient chatClient;
	private List<NetworkCommand> commands;
	private ThingToStringProtocol protocol;
	private int num;
	private GamePanel panel;

	public GameClient(GamePanel panel, Socket toServer) throws IOException {
		commands = new CopyOnWriteArrayList<>();
		protocol = new ThingToStringProtocol();
		chatClient = new ChatClient(toServer);
		this.panel = panel;
		chatClient.addMessageListener(new MessageListener() {

			@Override
			public void onReceive(MessageEvent e) {
				if (e.getMessage().getType() == Type.METADATA) {
					num = Integer.parseInt(e.getMessage().getData().replace("Your number is ", ""));
				}
			}
		});
		chatClient.addMessageListener(new MessageListener() {
			@Override
			public void onReceive(MessageEvent e) {

				if (e.getMessage().getSource() == Source.SERVER && e.getMessage().getType() == Type.DATA) {
					String data = e.getMessage().getData();
					if (data.equals("start")) {
					} else if (data.equals("end")) {
						panel.flush();
					} else if (data.startsWith("dynamic ")) {
						Element element = (Element) protocol.decode(data.substring(8));
						Drawer drawer = element.getDrawer();
						drawer.set("location", element.getLocation());
						panel.addDrawer(drawer);
					} else if (data.startsWith("static ")) {
						Element element = (Element) protocol.decode(data.substring(7));
						Drawer drawer = element.getDrawer();
						drawer.set("location", element.getLocation());
						panel.addStaticDrawer(drawer);
					}
				}
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
		return num;
	}

	public void sendCommands() {
		for (NetworkCommand command : commands) {
			chatClient.send(Message.data(Source.friend("FRIEND A"), command.toString()));
		}
		commands.clear();
	}

	public GamePanel getPanel() {
		return panel;
	}
}
