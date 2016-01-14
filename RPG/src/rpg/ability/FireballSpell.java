package rpg.ability;

import java.util.Arrays;
import java.util.List;

import rpg.Cost;
import rpg.Requirement;
import rpg.element.Entity;
import rpg.element.Fireball;
import rpg.geometry.Vector2D;
import rpg.logic.level.Level;

public class FireballSpell extends Spell {

	public FireballSpell() {
		super(2);
		set("speed", 192);
	}

	@Override
	public List<Requirement> getRequirements() {
		return Arrays.asList(Requirement.atLeast("mana", 1.0), Entity::isAlive,
				entity -> entity.hasTarget() && entity.getTarget().getInteger("id") != entity.getInteger("id")
						&& entity.getTarget() instanceof Entity);
	}

	@Override
	public List<Cost> getCosts() {
		return Arrays.asList(Cost.bar("mana", 1));
	}

	@Override
	public void onStart(Level level, Entity caster) {
		Vector2D location = caster.getLocation();
		Vector2D direction = caster.getTarget().getLocation().subtract(location).getUnitalVector();
		level.addDynamicElement(new Fireball(caster, location, direction, getDouble("speed")));
	}

	@Override
	public void onEnd(Level level, Entity caster) {

	}
}
