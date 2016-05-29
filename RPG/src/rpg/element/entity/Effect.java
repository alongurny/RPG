package rpg.element.entity;

import java.util.Map;

import rpg.Mechanism;
import rpg.logic.level.Level;

public class Effect extends Mechanism {

	private String[] keys;
	private Map<String, Boolean> map;
	private double duration;
	private double remainingTime;

	public Effect(double duration, String... keys) {
		this.duration = duration;
		this.remainingTime = duration;
		for (String key : keys) {
			map.put(key, true);
		}
		this.keys = keys;
	}

	private void reduceRemainingTime(double dt) {
		remainingTime -= dt;
	}

	public void onEnd() {
		for (String key : keys) {
			map.put(key, false);
		}
	}

	public boolean isAffecting() {
		return remainingTime > 0;
	}

	@Override
	public void update(Level level, double dt) {
		reduceRemainingTime(dt);
	}

}
