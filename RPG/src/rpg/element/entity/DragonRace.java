package rpg.element.entity;

import rpg.element.Entity;

public class DragonRace extends Race {
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