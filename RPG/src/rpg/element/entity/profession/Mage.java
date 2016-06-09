package rpg.element.entity.profession;

import rpg.ability.Ability;
import rpg.element.entity.Attribute;
import rpg.element.entity.Entity;

public abstract class Mage extends Profession {
	public Mage(Ability... abilities) {
		super(abilities);
	}

	@Override
	public double getMaxMana(Entity entity) {
		return 40 + entity.getAttribute(Attribute.INT);
	}

}
