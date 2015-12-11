package rpg;

import java.util.Arrays;
import java.util.List;

import rpg.element.Entity;
import rpg.element.Fireball;
import rpg.level.Level;
import rpg.physics.Vector2D;

public class FireballSpell extends Spell {

	private double distance;
	private double speed;

	public FireballSpell(Entity caster, double speed) {
		super(caster);
		distance = 32;
		this.speed = speed;
	}

	@Override
	public double getCooldown() {
		return 2;
	}

	@Override
	public void onCast(Level level, Entity caster) {
		Vector2D casterLocation = caster.getLocation();
		Vector2D casterDirection = caster.getDirection();
		Vector2D fireballLocation = casterLocation.add(casterDirection.multiply(distance));
		level.addElement(new Fireball(caster, fireballLocation, casterDirection, speed));
	}

	@Override
	public List<Pair<String, Double>> getRequirements() {
		return Arrays.asList(new Pair<>("mana", 1.0));

	}

}
