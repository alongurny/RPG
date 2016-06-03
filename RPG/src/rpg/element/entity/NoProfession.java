package rpg.element.entity;

import rpg.element.Entity;

public class NoProfession extends Profession {

	@Override
	public double getMaxMana(Entity entity) {
		return 0;
	}

}