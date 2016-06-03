package rpg.ability;

import java.util.Optional;

import rpg.element.Element;
import rpg.element.Entity;
import rpg.logic.level.Level;

public abstract class SelfAbility extends Ability {

	public SelfAbility(double maxCooldown) {
		super(maxCooldown, TargetType.SELF);
	}

	protected abstract boolean isCastable(Entity caster);

	@Override
	public final boolean isCastable(Entity caster, Optional<Element> element) {
		return isCastable(caster);
	}

	protected abstract void onCast(Level level, Entity caster);

	@Override
	public void onCast(Level level, Entity caster, Optional<Element> element) {
		onCast(level, caster);
	}

}
