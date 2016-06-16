package rpg.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import rpg.data.Tuple;
import rpg.element.Updatable;
import rpg.logic.level.Game;

/**
 * This class is used by {@link Game} to schedule events.
 * 
 * @author Alon
 *
 */
public class Timer implements Updatable {

	private List<Tuple<Double, Runnable>> list;
	private double time;

	/**
	 * Constructs a new timer.
	 */
	public Timer() {
		list = new CopyOnWriteArrayList<>();
	}

	/**
	 * Adds a new Runnable to this timer. The Runnable will be executed after
	 * the given amount of time.
	 * 
	 * @param time
	 *            the delay that after it the runnable will be executed
	 * @param run
	 *            the runnable
	 */
	public void add(double time, Runnable run) {
		list.add(Tuple.of(this.time + time, run));
	}

	public void update(Game game, double dt) {
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
