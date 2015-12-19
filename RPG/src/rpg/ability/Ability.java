package rpg.ability;

import java.util.List;

import rpg.Requirement;
import rpg.element.entity.Entity;
import rpg.logic.Level;
import rpg.ui.Drawable;

public abstract class Ability implements Drawable {

	public static final int WIDTH = 32;
	public static final int HEIGHT = 32;

	private double cooldown, maxCooldown;

	public Ability(double maxCooldown) {
		this.maxCooldown = maxCooldown;
		this.cooldown = 0;
	}

	public final double getCooldown() {
		return cooldown;
	}

	public void setCooldown(double cooldown) {
		this.cooldown = Math.max(0, Math.min(cooldown, maxCooldown));
	}

	public void reduceCooldown(double dcooldown) {
		setCooldown(cooldown - dcooldown);
	}

	public double getMaxCooldown() {
		return maxCooldown;
	}

	public abstract void onCast(Level level, Entity caster);

	public abstract List<Requirement> getRequirements();

	@Override
	public int getIndex() {
		return 0;
	}

}
