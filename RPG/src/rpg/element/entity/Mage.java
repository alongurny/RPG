package rpg.element.entity;

import rpg.ability.Ability;
import rpg.element.Entity;

public abstract class Mage extends Profession {
	public Mage(Ability... abilities) {
		super(abilities);
	}

	@Override
	public double getMaxMana(Entity entity) {
		return 40 + entity.getAttribute(Attribute.INT);
	}

}
