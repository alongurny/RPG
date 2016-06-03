package rpg.ability;

import java.util.Optional;

import rpg.element.Element;
import rpg.element.Entity;
import rpg.logic.level.Level;

public abstract class DurationAbility extends Ability {

	private double duration;

	public DurationAbility(double maxCooldown, double duration, TargetType targetType) {
		super(maxCooldown, targetType);
		this.duration = duration;
	}

	public DurationAbility(double maxCooldown, TargetType targetType) {
		this(maxCooldown, 0, targetType);
	}

	@Override
	public void onCast(Level level, Entity caster, Optional<Element> element) {
		onStart(level, caster, element);
		level.addTimer(duration, () -> onEnd(level, caster, element));
	}

	public abstract void onEnd(Level level, Entity caster, Optional<Element> element);

	public abstract void onStart(Level level, Entity caster, Optional<Element> element);
}
