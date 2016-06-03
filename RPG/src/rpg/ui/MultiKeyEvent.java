package rpg.ui;

import java.util.ArrayList;
import java.util.List;

public class MultiKeyEvent {
	private List<Integer> keys;

	public MultiKeyEvent(List<Integer> keys) {
		this.keys = new ArrayList<>();
		for (int key : keys) {
			this.keys.add(key);
		}
	}

	public boolean all(int... keys) {
		for (int key : keys) {
			if (!this.keys.contains(key)) {
				return false;
			}
		}
		return true;
	}

	public boolean any(int... keys) {
		for (int key : keys) {
			if (this.keys.contains(key)) {
				return true;
			}
		}
		return false;
	}

	public boolean get(int key) {
		return keys.contains(key);
	}

	public List<Integer> getKeys() {
		return keys;
	}
}
