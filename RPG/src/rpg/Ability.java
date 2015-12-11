package rpg;

import java.util.List;

import rpg.element.Entity;
import rpg.level.Level;

public abstract class Ability {

	public abstract double getCooldown();

	public abstract void onCast(Level level, Entity caster);

	public abstract List<Pair<String, Double>> getRequirements();

}
