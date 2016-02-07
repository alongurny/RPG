package rpg.network;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
	public void keysChange(MultiKeyEvent e) {
		Vector2D velocity = Vector2D.ZERO;
		if (e.get(KeyEvent.VK_UP) || e.get(KeyEvent.VK_DOWN)) {
			if (e.get(KeyEvent.VK_UP)) {
				velocity = velocity.add(Vector2D.NORTH);
			}
			if (e.get(KeyEvent.VK_DOWN)) {
				velocity = velocity.add(Vector2D.SOUTH);
			}
		}
		if (e.get(KeyEvent.VK_LEFT) || e.get(KeyEvent.VK_RIGHT)) {
			if (e.get(KeyEvent.VK_LEFT)) {
				velocity = velocity.add(Vector2D.WEST);
			}
			if (e.get(KeyEvent.VK_RIGHT)) {
				velocity = velocity.add(Vector2D.EAST);
			}
		}
		client.addCommand(
				new NetworkCommand("player " + client.getNumber() + " setVector velocityDirection " + velocity));
		if (!velocity.equals(Vector2D.ZERO)) {
			client.addCommand(new NetworkCommand("player " + client.getNumber() + " setVector direction " + velocity));
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		tracker.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		tracker.keyReleased(e);
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			client.addCommand(new NetworkCommand(String.format("player %s interact", client.getNumber())));
		} else if (e.getKeyCode() - '0' >= 1 && e.getKeyCode() - '0' <= 9) {
			client.addCommand(
					new NetworkCommand(String.format("player %s cast %s", client.getNumber(), e.getKeyCode() - '1')));
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		tracker.keyTyped(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Vector2D offset = client.getPanel().getOffset();
		Vector2D target = new Vector2D(e.getX() + offset.getX(), e.getY() + offset.getY());
		client.addCommand(new NetworkCommand(String.format("player %s setTarget %s", client.getNumber(), target)));
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
