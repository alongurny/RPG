package rpg.element.entity;

import rpg.element.Entity;

/**
 * A race is the biological origin of an individual. It determines some of their
 * attributes and even their abilities. This class is abstract so classes that
 * represent actual races can implement its methods. It's important that
 * different {@link Entity entities} do reference the same instance of
 * <code>Race</code>, since it may reference mutable objects such as abilities.
 */
public abstract class Race {

	public abstract int getAttribute(Attribute attr);

	public abstract double getMaxHealth(Entity entity);

	public abstract double getMaxMana(Entity entity);

	public abstract double getSpeed(Entity entity);

}
