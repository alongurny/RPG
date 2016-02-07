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

	public Ability(double maxCooldown) {
		set("maxCooldown", maxCooldown);
		setLimited("cooldown", 0.0, 0.0, maxCooldown);
	}

	public double getCooldown() {
		return getDouble("cooldown");
	}

	public boolean hasCooldown() {
		return getDouble("cooldown") > 0;
	}

	public void setCooldown(double cooldown) {
		set("cooldown", cooldown);
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
		return Drawer.concat(getSelfDrawer(),
				new AbilityDrawer(getDouble("cooldown"), getDouble("maxCooldown"), isCastable(player)));
	}

}
