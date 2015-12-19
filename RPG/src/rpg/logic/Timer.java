package rpg.logic;

import java.util.HashMap;
import java.util.Map.Entry;

public class Timer {

	private java.util.Map<Double, Runnable> actionMap;
	private double time;

	public Timer() {
		actionMap = new HashMap<>();
	}

	public void add(double time, Runnable run) {
		actionMap.put(this.time + time, run);
	}

	public void update(Level level, double dt) {
		time += dt;
		for (Entry<Double, Runnable> entry : actionMap.entrySet()) {
			if (time >= entry.getKey()) {
				entry.getValue().run();
				actionMap.remove(entry.getKey());
			}
		}
	}

}
