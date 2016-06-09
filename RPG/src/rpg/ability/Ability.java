package rpg.ability;

import rpg.element.Updatable;
import rpg.element.entity.Entity;
import rpg.element.entity.Player;
import rpg.graphics.AbilityDrawer;
import rpg.graphics.Drawer;
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
public abstract class Ability implements Updatable {

	private double cooldown;
	private double maxCooldown;
	private double mana;
	private boolean wasActive;
	private Entity caster;

	/**
	 * 
	 * @param maxCooldown
	 *            the cooldown of this ability
	 * @param mana
	 *            the mana cost of this ability
	 */
	protected Ability(double maxCooldown, double mana) {
		this.cooldown = 0;
		this.maxCooldown = maxCooldown;
		this.mana = mana;
	}

	/**
	 * Indicates whether this ability is ready to be cast by the given
	 * {@link Entity}. Returns true if all the following conditions are
	 * fulfilled:
	 * <ul>
	 * <li>{@link #getCooldown getCooldown()} <= 0</li>
	 * <li>{@link #hasEnoughMana(Entity) hasEnoughMana(caster)}</li>
	 * <li>{@link #isCastable(Entity) isCastable(caster)}</li>
	 * </ul>
	 * Otherwise returns false.
	 * 
	 * @param caster
	 *            the entity who casts this ability
	 * @return whether this ability is ready to cast
	 */
	public final boolean canCast(Entity caster) {
		return cooldown <= 0 && hasEnoughMana(caster) && isCastable(caster);
	}

	/**
	 * Casts this ability. This method subtracts mana from <code>caster</code>'s
	 * mana pool, then resets the cooldown to its maximum value, and then calls
	 * {@link #onCast(Game, Entity) onCast}.
	 * 
	 * @param game
	 *            the game
	 * @param caster
	 *            the entity who casts this ability
	 */
	public final void cast(Game game, Entity caster) {
		this.caster = caster;
		caster.subtractMana(mana);
		cooldown = maxCooldown;
		onCast(game, caster);
	}

	public double getCooldown() {
		return cooldown;
	}

	public Drawer getDrawer(Player player) {
		return getSelfDrawer()
				.andThen(new AbilityDrawer(cooldown, maxCooldown, hasEnoughMana(player), isCastable(player)));
	}

	public double getMaxCooldown() {
		return maxCooldown;
	}

	public abstract Drawer getSelfDrawer();

	public boolean hasEnoughMana(Entity entity) {
		return entity.getMana() >= mana;
	}

	@Override
	public void update(Game game, double dt) {
		cooldown = Math.max(0, cooldown - dt);
		boolean active = isActive(game, caster);
		if (active) {
			onUpdate(game, caster);
		} else if (wasActive) {
			onEnd(game, caster);
		}
		wasActive = active;
	}

	protected abstract boolean isActive(Game game, Entity caster);

	protected abstract boolean isCastable(Entity caster);

	protected abstract void onCast(Game game, Entity caster);

	protected abstract void onEnd(Game game, Entity caster);

	protected abstract void onUpdate(Game game, Entity caster);

}
