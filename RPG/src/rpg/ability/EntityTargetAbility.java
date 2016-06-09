package rpg.ability;

import rpg.element.entity.Entity;
import rpg.logic.level.Game;

/**
 * An ability that has a target which is an entity.
 *
 */
public abstract class EntityTargetAbility extends Ability {

	private Entity target;

	/**
	 * 
	 */
	protected EntityTargetAbility(double maxCooldown, double mana) {
		super(maxCooldown, mana);
	}

	@Override
	protected final boolean isActive(Game game, Entity caster) {
		return isActive(game, caster, target);
	}

	/**
	 * 
	 * @param game
	 * @param caster
	 * @param target
	 * @return
	 */
	protected boolean isActive(Game game, Entity caster, Entity target) {
		return false;
	}

	@Override
	protected final boolean isCastable(Entity caster) {
		return caster.getTarget().filter(t -> t instanceof Entity).isPresent()
				&& isCastable(caster, (Entity) caster.getTarget().get());
	}

	protected abstract boolean isCastable(Entity caster, Entity target);

	@Override
	protected final void onCast(Game game, Entity caster) {
		this.target = (Entity) caster.getTarget().get();
		onCast(game, caster, target);
	}

	protected abstract void onCast(Game game, Entity caster, Entity target);

	@Override
	protected final void onEnd(Game game, Entity caster) {
		onEnd(game, caster, target);
	}

	/**
	 * 
	 * @param game
	 * @param caster
	 * @param target
	 */
	protected void onEnd(Game game, Entity caster, Entity target) {

	}

	@Override
	protected final void onUpdate(Game game, Entity caster) {
		onUpdate(game, caster, target);
	}

	/**
	 * 
	 * @param game
	 * @param caster
	 * @param target
	 */
	protected void onUpdate(Game game, Entity caster, Entity target) {

	}
}
