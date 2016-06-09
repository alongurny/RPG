package rpg.element.entity.profession;

import java.util.Arrays;
import java.util.List;

import rpg.ability.Ability;
import rpg.ability.damage.DamageType;
import rpg.element.entity.Entity;

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

	protected Profession(Ability... abilities) {
		this.abilities = Arrays.asList(abilities);
	}

	public List<Ability> getAbilities() {
		return abilities;
	}

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

}
