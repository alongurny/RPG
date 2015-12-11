package rpg;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import rpg.element.Player;

public class PlayerClient implements KeyListener {

	private Player player;

	public PlayerClient(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
