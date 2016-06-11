package rpg.server;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import external.Messages;
import network.Switchboard;
import network.TcpClient;
import network.event.ConnectListener;
import network.event.ConnectionEvent;
import network.event.DisconnectListener;
import network.event.MessageEvent;
import network.event.MessageListener;
import network.message.Message;
import network.protocol.Protocol;
import rpg.element.Element;
import rpg.element.entity.profession.Profession;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.DrawerProtocol;
import rpg.graphics.MultiAbilityDrawer;
import rpg.graphics.ShowInventory;
import rpg.graphics.Translate;
import rpg.logic.Tuple;
import rpg.logic.level.Game;

/**
 * 
 * @author Alon
 *
 */
public class GameServer {

	private static void execute(String string, Game game, TcpClient client) {
		game.getPlayer(client).ifPresent(player -> {
			String[] arr = string.split(" ");
			String command = arr[0];
			switch (command) {
			case "moveBy":
				game.tryMoveBy(player, Vector2D.valueOf(arr[1]));
				break;
			case "setLocation":
				player.setLocation(Vector2D.valueOf(arr[1]));
				break;
			case "jump":
				player.tryJump(game);
				break;
			case "fall":
				player.tryFall(game);
				break;
			case "moveHorizontally":
				player.moveHorizontally(Double.parseDouble(arr[1]));
				break;
			case "onClick":
				game.onClick(player, Vector2D.valueOf(arr[1]));
				break;
			case "cast":
				player.tryCast(game, Integer.parseInt(arr[1]));
				break;
			case "interact":
				game.tryInteract(player);
				break;
			}
		});
	}

	private boolean firstConnection;
	private List<Tuple<String, TcpClient>> receivedCommands;
	private Switchboard server;
	private Timer timer;
	private Game game;
	private Protocol<Drawer, String> protocol;

	public GameServer(Game game) throws IOException {
		this.game = game;
		receivedCommands = new CopyOnWriteArrayList<>();
		timer = new Timer();
		protocol = new DrawerProtocol();
		server = new Switchboard(Messages.getInt("TCP.port"));
		server.addConnectListener(new ConnectListener() {

			@Override
			public void onConnect(ConnectionEvent e) {
				TcpClient c = e.getClient();
				c.addMessageListener(new MessageListener() {
					boolean received = false;

					@Override
					public void onReceive(MessageEvent e) {
						if (!received && e.getMessage().getType() == Message.Type.NORMAL) {
							String line = e.getMessage().getData();
							if (line.startsWith("selection")) {
								String[] arr = line.split(" ");
								try {
									game.addPlayer(c, (Profession) Class.forName(arr[1]).newInstance());
									if (game.isReady()) {
										firstConnection = true;
									}
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
							received = true;
						}
					}
				});
				c.addMessageListener(ev -> receivedCommands.add(Tuple.of(ev.getMessage().getData(), e.getClient())));
			}
		});
		server.addDisconnectListener(new DisconnectListener() {

			@Override
			public void onDisconnect(ConnectionEvent e) {
				game.removePlayer(e.getClient());
			}
		});
	}

	public boolean isAllowed(String command) {
		if (command.startsWith("nothing")) {
			return true;
		}
		return true;
	}

	public void start() {
		new Thread(this::run, "Wait for clients/Update game").start();
	}

	private void startReceiving() {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				for (Tuple<String, TcpClient> command : receivedCommands) {
					if (isAllowed(command.getFirst())) {
						execute(command.getFirst(), game, command.getSecond());
					}
				}
				receivedCommands.clear();
			}
		}, 0, 30);
	}

	private void startSending() {
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				send();
			}
		}, 0, 30);
	}

	private void run() {
		server.start();
		startReceiving();
		while (!game.isReady()) {
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		startSending();
		long last = System.nanoTime();
		while (server.isRunning()) {
			long now = System.nanoTime();
			game.update((now - last) * 1e-9);
			last = now;
		}
	}

	private void send() {
		server.send(Message.normal("start"));
		for (Element e : game.getDynamicElements()) {
			Translate t = new Translate((int) e.getLocation().getX(), (int) e.getLocation().getY());
			server.send(Message.normal("dynamic " + protocol.encode(t)));
			server.send(Message.normal("dynamic " + e.getDrawer()));
			server.send(Message.normal("dynamic " + protocol.encode(t.negate())));
		}
		if (firstConnection) {
			for (Element e : game.getStaticElements()) {
				String t1 = new Translate((int) e.getLocation().getX(), (int) e.getLocation().getY()).represent();
				String t2 = new Translate((int) -e.getLocation().getX(), (int) -e.getLocation().getY()).represent();
				server.send(Message.normal("static " + t1));
				server.send(Message.normal("static " + e.getDrawer()));
				server.send(Message.normal("static " + t2));
			}
			firstConnection = false;
		}
		server.forEach(c -> {
			MultiAbilityDrawer mad = new MultiAbilityDrawer(120, 480);
			game.getPlayer(c).ifPresent(p -> {
				p.getAbilities().forEach(a -> mad.addAbility(p, a));
				c.send(Message.normal("absolute " + mad.getDrawer()));
				c.send(Message.normal("inventory " + new ShowInventory(160, 160, p).getDrawer()));
				c.send(Message.normal("location " + p.getLocation()));
				c.send(Message
						.normal("dimensions " + new Vector2D(game.getGrid().getWidth(), game.getGrid().getHeight())));
			});
		});
		server.send(Message.normal("end"));
	}

}
