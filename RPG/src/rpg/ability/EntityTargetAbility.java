package rpg.ability;

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
public abstract class EntityTargetAbility extends Ability {

	private Entity target;

	protected EntityTargetAbility(double maxCooldown, double mana) {
		super(maxCooldown, mana);
	}

	@Override
	protected final boolean isCastable(Entity caster) {
		return caster.getTarget().filter(t -> t instanceof Entity).isPresent()
				&& isCastable(caster, (Entity) caster.getTarget().get());
	}

	@Override
	protected final void onCast(Game game, Entity caster) {
		this.target = (Entity) caster.getTarget().get();
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

	protected abstract boolean isCastable(Entity caster, Entity target);

	protected abstract void onCast(Game game, Entity caster, Entity target);

	protected void onUpdate(Game game, Entity caster, Entity target) {

	}

	protected void onEnd(Game game, Entity caster, Entity target) {

	}

	protected boolean isActive(Game game, Entity caster, Entity target) {
		return false;
	}

}
