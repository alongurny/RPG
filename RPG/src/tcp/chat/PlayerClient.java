package tcp.chat;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import event.MessageEvent;
import event.MessageListener;
import rpg.element.Element;
import rpg.element.entity.Player;
import rpg.logic.Game;
import rpg.network.NetworkCommand;
import rpg.physics.Vector2D;
import rpg.ui.MultiKeyEvent;
import rpg.ui.MultiKeyListener;
import tcp.chat.message.Message;
import tcp.chat.message.Message.Source;

public class PlayerClient implements KeyListener, MultiKeyListener {

	private Player player;
	private Game game;
	private List<NetworkCommand> commands;
	private ChatClient chatClient;

	public PlayerClient(Game game, Player player, Socket toServer) {
		this.player = player;
		this.game = game;
		commands = new CopyOnWriteArrayList<>();
		try {
			this.chatClient = new ChatClient(toServer, true);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.chatClient.addMessageListener(new MessageListener() {

			@Override
			public void onReceive(MessageEvent e) {
				System.out.println("receiving");
				String[] objectStrings = e.getMessage().getData().split("<><>");
				game.getLevel().getDynamicElements().clear();
				System.out.println("starting to deserialize " + objectStrings.length + " objects");
				int i = 0;
				for (String s : objectStrings) {
					if (s.length() > 1) {
						s = s.replace("<<newline>>", "\n");
						try {
							byte b[] = s.getBytes();
							ByteArrayInputStream bi = new ByteArrayInputStream(b);
							ObjectInputStream si = new ObjectInputStream(bi);
							game.getLevel().getDynamicElements().add((Element) si.readObject());
						} catch (Exception ex) {
							System.out.println(s);
							ex.printStackTrace();
						}
					}
				}
				PlayerClient.this.player = game.getLevel().getPlayer(0);
				System.out.println("end of receiving");
			}
		});
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

		commands.add(new NetworkCommand("player 0 setVelocityDirection " + velocity));
		if (!velocity.equals(Vector2D.ZERO)) {
			commands.add(new NetworkCommand("player 0 setDirection " + velocity));
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			commands.add(new NetworkCommand(String.format("player 0 interact")));
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() - '0' >= 1 && e.getKeyCode() - '0' <= player.getAbilityHandler().getSize()) {
			commands.add(new NetworkCommand(String.format("player 0 cast %s", e.getKeyCode() - '1')));
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
