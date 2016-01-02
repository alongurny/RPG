package rpg.ability;

import java.util.List;

import rpg.Mechanism;
import rpg.Requirement;
import rpg.element.entity.Entity;
import rpg.logic.level.Level;
import rpg.ui.Drawable;

public abstract class Ability extends Mechanism implements Drawable {

	public static final int WIDTH = 32;
	public static final int HEIGHT = 32;

	public Ability(double maxCooldown) {
		set("maxCooldown", maxCooldown);
		set("cooldown", 0.0);
	}

	public double getCooldown() {
		return getNumber("cooldown");
	}

	public void setCooldown(double cooldown) {
		set("cooldown", Math.max(0, Math.min(cooldown, getNumber("maxCooldown"))));
	}

	public void reduceCooldown(double dcooldown) {
		setCooldown(getCooldown() - dcooldown);
	}

	@Override
	public void update(Level level, double dt) {
		reduceCooldown(dt);
	}

	public abstract void onCast(Level level, Entity caster);

	public abstract List<Requirement> getRequirements();

}
