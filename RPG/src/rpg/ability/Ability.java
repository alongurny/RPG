package rpg.ability;

import java.util.Optional;

import rpg.Mechanism;
import rpg.element.Element;
import rpg.element.Entity;
import rpg.element.Player;
import rpg.graphics.AbilityDrawer;
import rpg.graphics.Drawer;
import rpg.logic.level.Level;

public abstract class Ability extends Mechanism {

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

	public boolean hasCooldown() {
		return cooldown > 0;
	}

	public void setCooldown(double cooldown) {
		this.cooldown = cooldown;
	}

	private void reduceCooldown(double dcooldown) {
		cooldown -= dcooldown;
	}

	public TargetType getTargetType() {
		return targetType;
	}

	@Override
	public void update(Level level, double dt) {
		reduceCooldown(dt);
	}

	public abstract boolean isCastable(Entity caster, Optional<Element> element);

	public abstract void onCast(Level level, Entity caster, Optional<Element> element);

	public abstract Drawer getSelfDrawer();

	public Drawer getDrawer(Player player) {
		return getSelfDrawer()
				.andThen(new AbilityDrawer(cooldown, maxCooldown, isCastable(player, player.getTarget())));
	}

	public double getMaxCooldown() {
		return maxCooldown;
	}

}
