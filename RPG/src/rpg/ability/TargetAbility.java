package rpg.ability;

import rpg.element.Element;
import rpg.element.Entity;
import rpg.logic.level.Game;

/**
 * This class represents an ability. Abilities can be cast to perform special
 * effects, for example: one may which to summon a fire sphere that will follow
 * them and attack nearby enemies.
 * 
 * Usually, abilities have requirements, such as mana. Additionally, abilities
 * have cooldown: a period between adjacent castings of the same ability.
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
	protected final boolean isCastable(Entity caster) {
		return caster.getTarget().isPresent() && isCastable(caster.getTarget().get());
	}

	@Override
	protected final void onCast(Game game, Entity caster) {
		this.target = caster.getTarget().get();
		onCast(game, caster, target);
	}

	@Override
	protected final boolean isActive(Game game, Entity caster) {
		return isActive(game, caster, target);
	}

	@Override
	protected final void onUpdate(Game game, Entity caster) {
		onUpdate(game, caster, target);
	}

	@Override
	protected final void onEnd(Game game, Entity caster) {
		onEnd(game, caster, target);
	}

	protected abstract void onCast(Game game, Entity caster, Element target);

	protected abstract void onUpdate(Game game, Entity caster, Element target);

	protected abstract void onEnd(Game game, Entity caster, Element target);

	protected abstract boolean isActive(Game game, Entity caster, Element target);

	protected abstract boolean isCastable(Element target);

}
