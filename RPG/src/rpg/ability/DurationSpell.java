package rpg.ability;

import rpg.element.entity.Entity;
import rpg.logic.Level;

public abstract class DurationSpell extends Spell {

	private double duration;

	public DurationSpell(double maxCooldown, double duration) {
		super(maxCooldown);
		this.duration = duration;
	}

	@Override
	public void onCast(Level level, Entity caster) {
		onStart(level, caster);
		level.addTimer(duration, () -> onEnd(level, caster));
	}

	public abstract void onStart(Level level, Entity caster);

	public abstract void onEnd(Level level, Entity caster);
}
