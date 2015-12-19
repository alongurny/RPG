package rpg.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import rpg.ability.AbilityHandler;
import rpg.element.entity.Player;
import rpg.logic.Game;
import rpg.physics.Vector2D;

public class PlayerClient implements KeyListener, MultiKeyListener {

	private Player player;
	private Game game;

	public PlayerClient(Game game, Player player) {
		this.player = player;
		this.game = game;
	}

	public Player getPlayer() {
		return player;
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
			game.getLevel().tryInteract(player);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_1) {
			AbilityHandler ah = player.getAbilityHandler();
			ah.tryCast(game.getLevel(), ah.getAbility(0));
		} else if (e.getKeyCode() == KeyEvent.VK_2) {
			AbilityHandler ah = player.getAbilityHandler();
			ah.tryCast(game.getLevel(), ah.getAbility(1));
		} else if (e.getKeyCode() == KeyEvent.VK_3) {
			AbilityHandler ah = player.getAbilityHandler();
			ah.tryCast(game.getLevel(), ah.getAbility(2));
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
