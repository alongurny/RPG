package rpg.element.entity.race;

import rpg.element.entity.Attribute;
import rpg.element.entity.Entity;

public class DragonRace extends Race {

	/**
	 * Constructs a new instance of this class. Different instances of Entity
	 * should NOT reference the same instance.
	 */
	public DragonRace() {
	}

	@Override
	public int getAttribute(Attribute attr) {
		return 10;
	}

	@Override
	public double getMaxHealth(Entity entity) {
		return 100;
	}

	@Override
	public double getMaxMana(Entity entity) {
		return 100;
	}

	public double getSpeed(Entity entity) {
		return 20;
	}
}