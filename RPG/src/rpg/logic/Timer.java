package rpg.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import rpg.Mechanism;
import rpg.logic.level.Level;

public class Timer extends Mechanism {

	private List<Tuple<Double, Runnable>> list;
	private double time;

	public Timer() {
		list = new CopyOnWriteArrayList<>();
	}

	public void add(double time, Runnable run) {
		list.add(Tuple.of(this.time + time, run));
	}

	public void update(Level level, double dt) {
		time += dt;
		List<Tuple<Double, Runnable>> toRemove = new ArrayList<>();
		for (Tuple<Double, Runnable> t : list) {
			if (time >= t.getFirst()) {
				t.getSecond().run();
				toRemove.add(t);
			}
		}
		list.removeAll(toRemove);
	}

}
