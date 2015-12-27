package tcp.chat;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import event.MessageEvent;
import event.MessageListener;
import rpg.element.entity.Player;
import rpg.logic.Game;
import rpg.logic.Level;
import rpg.logic.Level1;
import rpg.network.NetworkCommand;
import rpg.physics.Vector2D;
import rpg.ui.GameStation;
import rpg.ui.KeyTracker;
import rpg.ui.MultiKeyEvent;
import rpg.ui.MultiKeyListener;
import tcp.chat.message.Message;
import tcp.chat.message.Message.Source;
import tcp.chat.message.Message.Type;

public class GameClient implements KeyListener, MultiKeyListener {

	private Player player;
	private Game game;
	private List<NetworkCommand> commands;
	private ChatClient chatClient;
	private int num = -1;

	public GameClient(Game game, Player player, Socket toServer) {
		this.player = player;
		this.game = game;
		commands = new CopyOnWriteArrayList<>();
		try {
			this.chatClient = new ChatClient(toServer, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void run() {
		for (NetworkCommand c : commands) {
			c.execute(game);
			chatClient.send(Message.data(Source.SERVER, c.toString()));

		}
		commands.clear();
	}

	@Override
	public void keysChange(MultiKeyEvent e) {
		Vector2D velocity = Vector2D.ZERO;

		if (e.get(KeyEvent.VK_UP)) {
			velocity = velocity.add(Vector2D.NORTH);
		}
		if (e.get(KeyEvent.VK_DOWN)) {
			velocity = velocity.add(Vector2D.SOUTH);
		}
		if (e.get(KeyEvent.VK_LEFT)) {
			velocity = velocity.add(Vector2D.WEST);
		}
		if (e.get(KeyEvent.VK_RIGHT)) {
			velocity = velocity.add(Vector2D.EAST);
		}

		commands.add(new NetworkCommand("player " + num + " setVector velocityDirection " + velocity));
		if (!velocity.equals(Vector2D.ZERO)) {
			commands.add(new NetworkCommand("player " + num + " setVector direction " + velocity));
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			commands.add(new NetworkCommand(String.format("player %s interact", num)));
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() - '0' >= 1 && e.getKeyCode() - '0' <= player.getAbilityHandler().getSize()) {
			commands.add(new NetworkCommand(String.format("player %s cast %s", num, e.getKeyCode() - '1')));
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public static void main(String[] args) {
		Level level = new Level1();
		Game game = new Game(level);

		Socket s = null;
		try {
			s = new Socket("localhost", 1234);
		} catch (IOException e) {
			e.printStackTrace();
		}
		GameClient client = new GameClient(game, null, s);
		client.chatClient.addMessageListener(new MessageListener() {

			@Override
			public void onReceive(MessageEvent e) {
				if (e.getMessage().getType() == Type.METADATA && client.num == -1) {
					String data = e.getMessage().getData();
					if (data.startsWith("your number is ")) {
						client.num = Integer.parseInt(data.replace("your number is ", ""));
						client.player = level.getPlayer(client.num);
						GameStation gs = new GameStation(game, client.player) {
							@Override
							public void doSomething() {
								client.run();
							}
						};
						KeyTracker keyTracker = new KeyTracker();
						keyTracker.addMultiKeyListener(client);
						gs.addKeyListener(keyTracker);
						gs.addKeyListener(client);
						gs.start();
					}
				}
			}
		});
	}

}
