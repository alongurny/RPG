package rpg.element.entity.race;

import rpg.element.entity.Attribute;
import rpg.element.entity.Entity;

/**
 * A race is the biological origin of an individual. It determines some of their
 * attributes and even their abilities. This class is abstract so classes that
 * represent actual races can implement its methods. It's important that
 * different {@link Entity entities} do NOT reference the same instance of
 * <code>Race</code>, since it may reference mutable objects such as abilities.
 */
public abstract class Race {

	/**
	 * Returns the addition to the given entity's maximum mana, given by this
	 * <code>Race</code>.
	 * 
	 * @param entity
	 *            an entity
	 * @return the addition to <code>entity</code>'s maximum mana
	 */
	public abstract double getMaxMana(Entity entity);

	/**
	 * Returns the addition to the given entity's maximum health, given by this
	 * <code>Race</code>.
	 * 
	 * @param entity
	 *            an entity
	 * @return the addition to <code>entity</code>'s maximum health
	 */
	public abstract double getMaxHealth(Entity entity);

	/**
	 * Returns the addition to the given entity's natural speed, given by this
	 * <code>Race</code>.
	 * 
	 * @param entity
	 *            an entity
	 * @return the addition to <code>entity</code>'s speed
	 */
	public abstract double getSpeed(Entity entity);

	/**
	 * Returns the default value that is associated with this attribute.
	 * 
	 * @param attr
	 *            an attribute
	 * @return the value that is associated with this attribute
	 */
	public abstract int getAttribute(Attribute attr);

}
