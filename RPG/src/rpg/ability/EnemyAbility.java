package rpg.ability;

import java.util.Optional;

import rpg.element.Element;
import rpg.element.Entity;
import rpg.logic.level.Game;

public abstract class EnemyAbility extends Ability {

	public EnemyAbility(double maxCooldown) {
		super(maxCooldown, TargetType.ALLY);
	}

	protected abstract boolean isCastable(Entity caster, Entity element);

	@Override
	public final boolean isCastable(Entity caster, Optional<Element> element) {
		return element.isPresent() && element.get() instanceof Entity && !caster.isFriendly((Entity) element.get())
				&& isCastable(caster, (Entity) element.get());
	}

	protected abstract void onCast(Game game, Entity caster, Entity entity);

	@Override
	public final void onCast(Game game, Entity caster, Optional<Element> element) {
		onCast(game, caster, (Entity) element.get());
	}

}
