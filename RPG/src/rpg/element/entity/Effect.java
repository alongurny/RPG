package rpg.element.entity;

import rpg.Mechanism;
import rpg.logic.level.Level;

public class Effect extends Mechanism {

	private String[] keys;

	public Effect(double duration, String... keys) {
		set("duration", duration);
		set("remainingTime", duration);
		for (String key : keys) {
			set(key, true);
		}
		this.keys = keys;
	}

	private void reduceRemainingTime(double dt) {
		set("remainingTime", getDouble("remainingTime") - dt);
	}

	public void onEnd() {
		for (String key : keys) {
			set(key, false);
		}
	}

	public boolean isAffecting() {
		return getDouble("remainingTime") > 0;
	}

	@Override
	public void update(Level level, double dt) {
		reduceRemainingTime(dt);
	}

}
