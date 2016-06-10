package rpg.ability;

import rpg.element.Element;
import rpg.element.entity.Entity;
import rpg.logic.level.Game;

/**
 * An ability that has a target which is an element.
 * 
 * @author Alon
 *
 */
public abstract class TargetAbility extends Ability {

	private Element target;

	protected TargetAbility(double maxCooldown, double mana) {
		super(maxCooldown, mana);
	}

	@Override
	protected final boolean isActive(Game game, Entity caster) {
		return isActive(game, caster, target);
	}

	protected abstract boolean isActive(Game game, Entity caster, Element target);

	protected abstract boolean isCastable(Element target);

	@Override
	protected final boolean isCastable(Entity caster) {
		return caster.getTarget().isPresent() && isCastable(caster.getTarget().get());
	}

	@Override
	protected final void onCast(Game game, Entity caster) {
		this.target = caster.getTarget().get();
		onCast(game, caster, target);
	}

	protected abstract void onCast(Game game, Entity caster, Element target);

	@Override
	protected final void onEnd(Game game, Entity caster) {
		onEnd(game, caster, target);
	}

	protected abstract void onEnd(Game game, Entity caster, Element target);

	@Override
	protected final void onUpdate(Game game, Entity caster) {
		onUpdate(game, caster, target);
	}

	protected abstract void onUpdate(Game game, Entity caster, Element target);

}
