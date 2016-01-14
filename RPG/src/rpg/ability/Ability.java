package rpg.ability;

import java.util.List;

import rpg.Cost;
import rpg.Mechanism;
import rpg.Requirement;
import rpg.element.Entity;
import rpg.element.Player;
import rpg.graphics.draw.AbilityDrawer;
import rpg.graphics.draw.Drawable;
import rpg.logic.level.Level;

public abstract class Ability extends Mechanism {

	public static final int WIDTH = 32;
	public static final int HEIGHT = 32;

	public Ability(double maxCooldown) {
		set("maxCooldown", maxCooldown);
		set("cooldown", 0.0);
	}

	public double getCooldown() {
		return getDouble("cooldown");
	}

	public boolean hasCooldown() {
		return getDouble("cooldown") > 0;
	}

	public void setCooldown(double cooldown) {
		set("cooldown", Math.max(0, Math.min(cooldown, getDouble("maxCooldown"))));
	}

	private void reduceCooldown(double dcooldown) {
		setCooldown(getCooldown() - dcooldown);
	}

	@Override
	public void update(Level level, double dt) {
		reduceCooldown(dt);
	}

	public abstract void onCast(Level level, Entity caster);

	public abstract List<Requirement> getRequirements();

	public abstract List<Cost> getCosts();

	public Drawable getDrawer(Player player) {
		return new AbilityDrawer(getDouble("cooldown"), getDouble("maxCooldown"), player.isCastable(this));
	}

}
