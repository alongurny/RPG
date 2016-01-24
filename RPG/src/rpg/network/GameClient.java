package rpg.network;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import protocol.ThingToStringProtocol;
import rpg.element.Element;
import rpg.element.Player;
import rpg.geometry.Vector2D;
import rpg.graphics.draw.Drawer;
import rpg.graphics.draw.MultiAbilityDrawer;
import rpg.ui.GamePanel;
import rpg.ui.GameStation;
import tcp.ChatClient;
import tcp.message.Message;
import tcp.message.Message.Type;
import event.MessageEvent;
import event.MessageListener;

public class GameClient {

	private ChatClient chatClient;
	private List<NetworkCommand> commands;
	private ThingToStringProtocol protocol;
	private Optional<Integer> num;
	private GamePanel panel;

	public GameClient(GamePanel panel, Socket toServer) throws IOException {
		commands = new CopyOnWriteArrayList<>();
		protocol = new ThingToStringProtocol();
		chatClient = new ChatClient(toServer);
		this.panel = panel;
		num = Optional.empty();
		chatClient.addMessageListener(new MessageListener() {
			@Override
			public void onReceive(MessageEvent e) {
				if (e.getMessage().getType() == Type.METADATA) {
					String data = e.getMessage().getData();
					if (data.startsWith("number ") && !num.isPresent()) {
						num = Optional.of(Integer.valueOf(data.replace(
								"number ", "")));
					}
				}
			}
		});
		chatClient.addMessageListener(new MessageListener() {
			@Override
			public void onReceive(MessageEvent e) {
				String data = e.getMessage().getData();
				if (data.equals("start")) {
				} else if (data.equals("end")) {
					panel.flush();
				} else if (data.startsWith("dynamic ")) {
					Element element = (Element) protocol.decode(data
							.substring(8));
					Drawer drawer = element.getDrawer();
					drawer.set("location", element.getLocation());
					drawer.set("z-index", element.getIndex());
					panel.addDrawer(drawer);
					if (element instanceof Player && num.isPresent()
							&& element.getInteger("id") == num.get()) {
						MultiAbilityDrawer d = new MultiAbilityDrawer();
						d.set("location", new Vector2D(32, 460));
						d.set("z-index", 1000);
						Player player = (Player) element;
						player.getAbilities().forEach(
								ability -> d.addAbility(player, ability));
						panel.addDrawer(d);
					}
				} else if (data.startsWith("static ")) {
					Element element = (Element) protocol.decode(data
							.substring(7));
					Drawer drawer = element.getDrawer();
					drawer.set("location", element.getLocation());
					drawer.set("z-index", element.getIndex());
					panel.addStaticDrawer(drawer);
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
}
