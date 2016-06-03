package rpg.element.entity;

import rpg.element.Entity;

public class Mage extends Profession {

	@Override
	public double getMaxMana(Entity entity) {
		return 40 + entity.getModifier(Attribute.INT) * 8;
	}

}