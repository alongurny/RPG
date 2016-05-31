package rpg.logic;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import rpg.Mechanism;
import rpg.logic.level.Level;

public class Timer extends Mechanism {

	private Map<Double, Runnable> actionMap;
	private double time;

	public Timer() {
		actionMap = new ConcurrentHashMap<>();
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
