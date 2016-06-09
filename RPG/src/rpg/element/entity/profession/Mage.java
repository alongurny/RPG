package rpg.element.entity.profession;

import rpg.ability.Ability;
import rpg.element.entity.Attribute;
import rpg.element.entity.Entity;

/**
 * A profession that provides maximum mana that is proportional to the entity's
 * {@link Attribute#INT intelligence}.
 * 
 * @author Alon
 *
 */
public abstract class Mage extends Profession {

	/**
	 * @see Profession#Profession(Ability...)
	 */
	public Mage(Ability... abilities) {
		super(abilities);
	}

	/**
	 * 
	 * @return 40 + INT
	 */
	@Override
	public double getMaxMana(Entity entity) {
		return 40 + entity.getAttribute(Attribute.INT);
	}

}
