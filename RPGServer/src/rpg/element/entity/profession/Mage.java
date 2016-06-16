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
	 * @param abilities
	 *            the abilities that this profession has
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

	@Override
	public double getMaxHealth(Entity entity) {
		return 42 + 0.5 * entity.getAttribute(Attribute.CON);
	}

	@Override
	public int getAttribute(Attribute attr) {
		return attr == Attribute.INT ? 2 : 0;
	}

}
