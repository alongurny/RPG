package rpg.ability;

import java.util.Optional;

import rpg.element.Element;
import rpg.element.Entity;
import rpg.logic.level.Level;

public abstract class EntityAbility extends Ability {

	public EntityAbility(double maxCooldown) {
		super(maxCooldown, TargetType.ALLY);
	}

	protected abstract boolean isCastable(Entity caster, Entity element);

	@Override
	public final boolean isCastable(Entity caster, Optional<Element> element) {
		return element.isPresent() && element.get() instanceof Entity && isCastable(caster, (Entity) element.get());
	}

	protected abstract void onCast(Level level, Entity caster, Entity entity);

	@Override
	public final void onCast(Level level, Entity caster, Optional<Element> element) {
		onCast(level, caster, (Entity) element.get());
	}

}
