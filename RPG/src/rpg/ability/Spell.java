package rpg.ability;

import rpg.element.entity.Entity;
import rpg.logic.level.Level;

public abstract class Spell extends Ability {

	private double duration;

	public Spell(double maxCooldown, double duration) {
		super(maxCooldown);
		this.duration = duration;
	}

	public Spell(double maxCooldown) {
		this(maxCooldown, 0);
	}

	@Override
	public void onCast(Level level, Entity caster) {
		onStart(level, caster);
		level.addTimer(duration, () -> onEnd(level, caster));
	}

	public abstract void onStart(Level level, Entity caster);

	public abstract void onEnd(Level level, Entity caster);
}
