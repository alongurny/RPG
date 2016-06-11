package rpg.element.entity.profession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rpg.ability.Ability;
import rpg.ability.damage.DamageType;
import rpg.ability.physical.BasicAttack;
import rpg.element.entity.Attribute;
import rpg.element.entity.Entity;
import rpg.graphics.Drawer;

/**
 * A profession is what an individual does for living. One's profession includes
 * most of their abilities and some of their attributes. This class is abstract
 * so classes that represent actual professions can implement its methods. It's
 * important that different {@link Entity entities} do reference the same
 * instance of <code>Profession</code>, since it may reference mutable objects
 * such as abilities.
 */
public abstract class Profession {

	private List<Ability> abilities;

	/**
	 * 
	 * Constructs a new instance of this class. An entity that has this
	 * profession will be able to use the given abilities. Different instances
	 * of {@link Entity} should NOT reference the same instance.
	 * 
	 * @param abilities
	 *            an array of abilities that can be used with this profession
	 */
	protected Profession(Ability... abilities) {
		this.abilities = new ArrayList<>();
		this.abilities.add(new BasicAttack());
		this.abilities.addAll(Arrays.asList(abilities));
	}

	/**
	 * Returns the abilities that the entity who has this profession can use.
	 * 
	 * @return the abilities associated with this profession
	 */
	public List<Ability> getAbilities() {
		return abilities;
	}

	/**
	 * Returns the addition to the given entity's maximum health, given by this
	 * <code>Profession</code>.
	 * 
	 * @param entity
	 *            an entity
	 * @return the addition to <code>entity</code>'s maximum health
	 */
	public abstract double getMaxHealth(Entity entity);

	/**
	 * Returns the addition to the given entity's maximum mana, given by this
	 * <code>Profession</code>.
	 * 
	 * @param entity
	 *            an entity
	 * @return the addition to <code>entity</code>'s maximum mana
	 */
	public abstract double getMaxMana(Entity entity);

	/**
	 * Returns the addition to the given entity's resistance to the given damage
	 * type, given by this <code>Profession</code>.
	 * 
	 * @param entity
	 *            an entity
	 * @param damageType
	 *            a type of damage
	 * @return the addition to <code>entity</code>'s resistance to the given
	 *         <code>DamageType</code>
	 */
	public abstract double getResistance(Entity entity, DamageType damageType);

	/**
	 * Returns the default value that is associated with this attribute.
	 * 
	 * @param attr
	 *            an attribute
	 * @return the value that is associated with this attribute
	 */
	public abstract int getAttribute(Attribute attr);

	public abstract Drawer getLeftDrawer();

	public abstract Drawer getRightDrawer();

}
