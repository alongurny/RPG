package rpg.element.entity;

import rpg.Thing;
import rpg.element.Entity;

public abstract class Profession extends Thing {

	public static final Mage MAGE = new Mage();
	public static final Warrior WARRIOR = new Warrior();
	public static final NoProfession NONE = new NoProfession();

	public abstract double getMaxMana(Entity entity);

}
