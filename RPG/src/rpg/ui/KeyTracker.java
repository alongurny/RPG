package rpg.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class KeyTracker implements KeyListener {

	private List<Integer> keys;
	private List<MultiKeyListener> listeners;

	public KeyTracker() {
		keys = new ArrayList<>();
		listeners = new ArrayList<>();
	}

	public void addMultiKeyListener(MultiKeyListener multiKeyListener) {
		this.listeners.add(multiKeyListener);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys.add(e.getKeyCode());
		listeners.forEach(li -> li.keysChange(new MultiKeyEvent(keys)));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys.removeIf(key -> key == e.getKeyCode());
		listeners.forEach(li -> li.keysChange(new MultiKeyEvent(keys)));
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public void removeMultiKeyListener(MultiKeyListener multiKeyListener) {
		this.listeners.remove(multiKeyListener);
	}

}
