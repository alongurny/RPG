package rpg.ability;

import rpg.element.Element;
import rpg.element.entity.Entity;
import rpg.logic.level.Game;

/**
 * An ability that has a target which is an entity.
 *
 */
public abstract class TargetAbility extends Ability {

	private Element target;

	/**
	 * Constructs a new ability with a target.
	 */
	protected TargetAbility(double maxCooldown, double mana) {
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
	protected boolean isActive(Game game, Entity caster, Element target) {
		return false;
	}

	@Override
	protected final boolean isCastable(Entity caster) {
		return caster.getTarget().filter(t -> t instanceof Entity).isPresent()
				&& isCastable(caster, caster.getTarget().get());
	}

	/**
	 * Called by {@link #isCastable(Entity)} with the caster's target as a
	 * parameter.
	 * 
	 * @param game
	 *            the game
	 * @param caster
	 *            the caster
	 * @param target
	 *            the caster's target
	 */
	protected abstract boolean isCastable(Entity caster, Element target);

	@Override
	protected final void onCast(Game game, Entity caster) {
		this.target = caster.getTarget().get();
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
	protected abstract void onCast(Game game, Entity caster, Element target);

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
	protected void onEnd(Game game, Entity caster, Element target) {

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
	protected void onUpdate(Game game, Entity caster, Element target) {

	}
}
