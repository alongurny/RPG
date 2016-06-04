package rpg.element.entity;

import rpg.element.Entity;

public class Human extends Race {
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