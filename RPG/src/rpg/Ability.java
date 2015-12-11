package rpg;

import java.util.List;

import rpg.element.Entity;
import rpg.level.Level;
import rpg.ui.Drawable;

public abstract class Ability implements Drawable {

	public static final int WIDTH = 32;
	public static final int HEIGHT = 32;

	public abstract double getCooldown();

	public abstract void onCast(Level level, Entity caster);

	public abstract List<Pair<String, Double>> getRequirements();

}
