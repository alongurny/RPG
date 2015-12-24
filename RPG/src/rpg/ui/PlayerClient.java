package rpg.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import rpg.element.entity.Player;
import rpg.logic.Game;
import rpg.network.NetworkCommand;
import rpg.physics.Vector2D;

public class PlayerClient implements KeyListener, MultiKeyListener {

	private Player player;
	private Game game;
	private List<NetworkCommand> commands;
	private Socket socket;

	public PlayerClient(Game game, Player player, Socket socket) {
		this.player = player;
		this.game = game;
		commands = new ArrayList<>();
		this.socket = socket;
	}

	public Player getPlayer() {
		return player;
	}

	public void run() {
		for (NetworkCommand c : commands) {
			c.execute(game);

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

		player.setVelocityDirection(velocity);
		if (!velocity.equals(Vector2D.ZERO)) {
			player.setDirection(velocity);
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
