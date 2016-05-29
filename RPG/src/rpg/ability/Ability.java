package rpg.ability;

import rpg.Mechanism;
import rpg.element.Element;
import rpg.element.Entity;
import rpg.element.Player;
import rpg.graphics.draw.AbilityDrawer;
import rpg.graphics.draw.Drawer;
import rpg.logic.level.Level;

public abstract class Ability extends Mechanism {

	public static final int WIDTH = 32;
	public static final int HEIGHT = 32;

	private double maxCooldown;
	private double cooldown;

	public Ability(double maxCooldown) {
		this.maxCooldown = maxCooldown;
		this.cooldown = 0;
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
		setCooldown(getCooldown() - dcooldown);
	}

	@Override
	public void update(Level level, double dt) {
		reduceCooldown(dt);
	}

	public abstract boolean isCastable(Entity caster, Element... elements);

	public abstract void onCast(Level level, Entity caster, Element... elements);

	public abstract Drawer getSelfDrawer();

	public Drawer getDrawer(Player player) {
		return Drawer.concat(getSelfDrawer(), new AbilityDrawer(cooldown, maxCooldown, isCastable(player)));
	}

}
