package rpg;

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

	@Override
	public void keyTyped(KeyEvent e) {

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

	public void addMultiKeyListener(MultiKeyListener multiKeyListener) {
		this.listeners.add(multiKeyListener);
	}

	public void removeMultiKeyListener(MultiKeyListener multiKeyListener) {
		this.listeners.remove(multiKeyListener);
	}

}
