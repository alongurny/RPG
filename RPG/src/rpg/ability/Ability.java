package rpg.ability;

import rpg.Mechanism;
import rpg.element.Entity;
import rpg.element.Player;
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
public abstract class Ability implements Mechanism {

	private double cooldown;
	private double maxCooldown;
	private double mana;
	private boolean wasActive;
	private Entity caster;

	protected Ability(double maxCooldown, double mana) {
		this.cooldown = 0;
		this.maxCooldown = maxCooldown;
		this.mana = mana;
	}

	public final boolean canCast(Entity caster) {
		return cooldown <= 0 && hasEnoughMana(caster) && isCastable(caster);
	}

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

	protected abstract boolean isActive(Game game, Entity caster);

	protected abstract boolean isCastable(Entity caster);

	protected abstract void onCast(Game game, Entity caster);

	protected abstract void onEnd(Game game, Entity caster);

	protected abstract void onUpdate(Game game, Entity caster);

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

}
