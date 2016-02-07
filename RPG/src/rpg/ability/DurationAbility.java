package rpg.ability;

import rpg.element.Element;
import rpg.element.Entity;
import rpg.logic.level.Level;

public abstract class DurationAbility extends Ability {

	private double duration;

	public DurationAbility(double maxCooldown, double duration) {
		super(maxCooldown);
		this.duration = duration;
	}

	public DurationAbility(double maxCooldown) {
		this(maxCooldown, 0);
	}

	@Override
	public void onCast(Level level, Entity caster, Element... elements) {
		onStart(level, caster, elements);
		level.addTimer(duration, () -> onEnd(level, caster, elements));
	}

	public abstract void onStart(Level level, Entity caster, Element... elements);

	public abstract void onEnd(Level level, Entity caster, Element... elements);
}
