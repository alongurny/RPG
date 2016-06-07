package rpg.network;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import rpg.geometry.Vector2D;
import rpg.ui.KeyTracker;
import rpg.ui.MultiKeyEvent;
import rpg.ui.MultiKeyListener;

public class IOHandler implements KeyListener, MultiKeyListener, MouseListener {

	private GameClient client;
	private KeyTracker tracker;

	public IOHandler(GameClient client) {
		this.client = client;
		this.tracker = new KeyTracker();
		tracker.addMultiKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		tracker.keyPressed(e);
		if (e.getKeyCode() == KeyEvent.VK_E) {
			client.setShowInventory(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		tracker.keyReleased(e);
		if (e.getKeyCode() == KeyEvent.VK_Z) {
			client.addCommand("interact");
		} else if (e.getKeyCode() - '0' >= 1 && e.getKeyCode() - '0' <= 9) {
			client.addCommand("cast " + (e.getKeyCode() - '1'));
		}
		if (e.getKeyCode() == KeyEvent.VK_E) {
			client.setShowInventory(false);
		}
	}

	@Override
	public void keysChange(MultiKeyEvent e) {
		if (e.get(KeyEvent.VK_UP) || e.get(KeyEvent.VK_W) || e.get(KeyEvent.VK_SPACE)) {
			client.addCommand("jump");
		}
		if (e.get(KeyEvent.VK_DOWN) || e.get(KeyEvent.VK_S)) {
		}
		double direction = 0;
		if (e.get(KeyEvent.VK_LEFT) || e.get(KeyEvent.VK_A)) {
			direction -= 1;
		}
		if (e.get(KeyEvent.VK_RIGHT) || e.get(KeyEvent.VK_D)) {
			direction += 1;
		}
		client.addCommand("moveHorizontally " + direction);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		tracker.keyTyped(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			Vector2D offset = client.getPanel().getOffset();
			Vector2D target = new Vector2D(e.getX(), e.getY()).subtract(offset);
			client.addCommand("onClick " + target);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
