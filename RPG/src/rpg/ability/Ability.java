package rpg.ability;

import java.util.Optional;

import rpg.Mechanism;
import rpg.element.Element;
import rpg.element.Entity;
import rpg.element.Player;
import rpg.graphics.AbilityDrawer;
import rpg.graphics.Drawer;
import rpg.logic.level.Level;

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

	public static final int WIDTH = 32;
	public static final int HEIGHT = 32;

	private double cooldown;
	private double maxCooldown;
	private TargetType targetType;

	public Ability(double maxCooldown, TargetType targetType) {
		this.cooldown = 0;
		this.maxCooldown = maxCooldown;
		this.targetType = targetType;
	}

	public double getCooldown() {
		return cooldown;
	}

	public Drawer getDrawer(Player player) {
		return getSelfDrawer()
				.andThen(new AbilityDrawer(cooldown, maxCooldown, isCastable(player, player.getTarget())));
	}

	public double getMaxCooldown() {
		return maxCooldown;
	}

	public abstract Drawer getSelfDrawer();

	public TargetType getTargetType() {
		return targetType;
	}

	public boolean hasCooldown() {
		return cooldown > 0;
	}

	public abstract boolean isCastable(Entity caster, Optional<Element> element);

	public abstract void onCast(Level level, Entity caster, Optional<Element> element);

	private void reduceCooldown(double dcooldown) {
		cooldown -= dcooldown;
	}

	public void setCooldown(double cooldown) {
		this.cooldown = cooldown;
	}

	@Override
	public void update(Level level, double dt) {
		reduceCooldown(dt);
	}

}
