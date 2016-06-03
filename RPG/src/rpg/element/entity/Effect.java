package rpg.element.entity;

import rpg.Mechanism;
import rpg.logic.level.Level;

public class Effect implements Mechanism {

	private String key;
	private double duration;
	private double remainingTime;

	public Effect(double duration, String key) {
		this.duration = duration;
		this.remainingTime = duration;
		this.key = key;
	}

	public double getDuration() {
		return duration;
	}

	public String getKey() {
		return key;
	}

	public boolean isAffecting() {
		return remainingTime > 0;
	}

	public void onEnd() {

	}

	private void reduceRemainingTime(double dt) {
		remainingTime -= dt;
	}

	@Override
	public void update(Level level, double dt) {
		reduceRemainingTime(dt);
	}

}
