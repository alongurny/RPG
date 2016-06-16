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
	 * Constructs a new ability.
	 * 
	 * @param maxCooldown
	 *            the cooldown of this ability
	 * @param mana
	 *            the mana cost of this ability
	 */
	protected Ability(double maxCooldown, double mana) {
		this.cooldown = maxCooldown;
		this.maxCooldown = maxCooldown;
		this.mana = mana;
	}

	/**
	 * Indicates whether this ability is ready to be cast by the given
	 * {@link Entity}. Returns true if all the following conditions are
	 * fulfilled:
	 * <ul>
	 * <li>{@link #getCooldown getCooldown()} &lt;= 0</li>
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
		return caster.isAlive() && cooldown <= 0 && hasEnoughMana(caster) && isCastable(caster);
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

	/**
	 * Returns the remaining cooldown of this ability.
	 * 
	 * @return the remaining cooldown of this ability
	 */
	public double getCooldown() {
		return cooldown;
	}

	/**
	 * Returns an appropriate drawer for the given player.
	 * 
	 * @param player
	 *            the player that uses this ability
	 * @return an ability drawer
	 */
	public Drawer getDrawer(Player player) {
		return getSelfDrawer()
				.andThen(new AbilityDrawer(cooldown, maxCooldown, hasEnoughMana(player), isCastable(player)));
	}

	/**
	 * Returns the maximum possible cooldown of this ability.
	 * 
	 * @return the maximum possible cooldown of this ability
	 */
	public double getMaxCooldown() {
		return maxCooldown;
	}

	/**
	 * The drawer that draws the actual image of the ability.
	 * 
	 * @return the drawer that draws this ability.
	 */
	protected abstract Drawer getSelfDrawer();

	/**
	 * Returns whether the given entity has enough mana to cast this ability.
	 * 
	 * @param entity
	 *            the caster
	 * @return <code>true</code> if the caster has enough mana,
	 *         <code>false</code> otherwise
	 */
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

	/**
	 * After this ability is cast, this method is used repeatedly to check if
	 * {@link #onUpdate(Game, Entity) onUpdate} should be called again. When it
	 * returns false, {@link #onEnd(Game, Entity) onEnd} is called.
	 * 
	 * @param game
	 *            the game
	 * @param caster
	 *            the caster
	 * @return <code>true</code> if this ability is active, false otherwise
	 */
	protected abstract boolean isActive(Game game, Entity caster);

	/**
	 * Returns <code>true</code> if this method can be cast for the given
	 * caster, <code>false</code> otherwise.
	 * 
	 * @param caster
	 *            the caster
	 * @return <code>true</code> if this ability is castable, <code>false</code>
	 *         otherwise
	 */
	protected abstract boolean isCastable(Entity caster);

	/**
	 * This method is called when this ability is cast.
	 * 
	 * @param game
	 *            the game
	 * @param caster
	 *            the caster
	 */
	protected abstract void onCast(Game game, Entity caster);

	/**
	 * This method is called when it is no longer active after cast.
	 * 
	 * @param game
	 *            the game
	 * @param caster
	 *            the caster
	 */
	protected abstract void onEnd(Game game, Entity caster);

	/**
	 * This method is called while being active after cast.
	 * 
	 * @param game
	 *            the game
	 * @param caster
	 *            the caster
	 */
	protected abstract void onUpdate(Game game, Entity caster);

	/**
	 * Sets the cooldown.
	 * 
	 * @param cooldown
	 *            the new cooldown to set
	 */
	public void setCooldown(double cooldown) {
		this.cooldown = cooldown;
	}

}
