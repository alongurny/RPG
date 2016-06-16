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
	 * Constructs a new ability with an entity target.
	 */
	protected EntityTargetAbility(double maxCooldown, double mana) {
		super(maxCooldown, mana);
	}

	@Override
	protected final boolean isActive(Game game, Entity caster) {
		return isActive(game, caster, target);
	}

	/**
	 * Called by {@link #isActive(Game, Entity)} with the caster's target as a
	 * parameter.
	 * 
	 * @param game
	 *            the game
	 * @param caster
	 *            the caster
	 * @param target
	 *            the caster's target
	 */
	protected boolean isActive(Game game, Entity caster, Entity target) {
		return false;
	}

	@Override
	protected final boolean isCastable(Entity caster) {
		return caster.getTarget().filter(t -> t instanceof Entity).isPresent()
				&& isCastable(caster, (Entity) caster.getTarget().get());
	}

	/**
	 * Called by {@link #isCastable(Entity)} with the caster's target as a
	 * parameter.
	 * 
	 * @param caster
	 *            the caster
	 * @param target
	 *            the caster's target
	 * @return if this ability is castable
	 */
	protected abstract boolean isCastable(Entity caster, Entity target);

	@Override
	protected final void onCast(Game game, Entity caster) {
		this.target = (Entity) caster.getTarget().get();
		onCast(game, caster, target);
	}

	/**
	 * Called by {@link #onCast(Game, Entity)} with the caster's target as a
	 * parameter.
	 * 
	 * @param game
	 *            the game
	 * @param caster
	 *            the caster
	 * @param target
	 *            the caster's target
	 */
	protected abstract void onCast(Game game, Entity caster, Entity target);

	@Override
	protected final void onEnd(Game game, Entity caster) {
		onEnd(game, caster, target);
	}

	/**
	 * Called by {@link #onEnd(Game, Entity)} with the caster's target as a
	 * parameter.
	 * 
	 * @param game
	 *            the game
	 * @param caster
	 *            the caster
	 * @param target
	 *            the caster's target
	 */
	protected void onEnd(Game game, Entity caster, Entity target) {

	}

	@Override
	protected final void onUpdate(Game game, Entity caster) {
		onUpdate(game, caster, target);
	}

	/**
	 * Called by {@link #onUpdate(Game, Entity)} with the caster's target as a
	 * parameter.
	 * 
	 * @param game
	 *            the game
	 * @param caster
	 *            the caster
	 * @param target
	 *            the caster's target
	 */
	protected void onUpdate(Game game, Entity caster, Entity target) {

	}
}
