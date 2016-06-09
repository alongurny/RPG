package rpg.element.entity.race;

import rpg.element.entity.Attribute;
import rpg.element.entity.Entity;

/**
 * Human is a race.
 * 
 */
public class Human extends Race {

	/**
	 * Constructs a new instance of this class. Different instances of Entity
	 * should NOT reference the same instance.
	 */
	public Human() {
		super();
	}

	@Override
	public int getAttribute(Attribute attr) {
		return 0;
	}

	@Override
	public double getMaxHealth(Entity entity) {
		return 4 * entity.getAttribute(Attribute.CON);
	}

	@Override
	public double getMaxMana(Entity entity) {
		return 0;
	}

	public double getSpeed(Entity entity) {
		return 64;
	}
}