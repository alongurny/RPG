package rpg.element.entity;

import java.util.Arrays;
import java.util.List;

import rpg.ability.Ability;
import rpg.element.DamageType;
import rpg.element.Entity;

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

	public abstract double getMaxMana(Entity entity);

	public abstract double getResistance(Entity entity, DamageType type);

}
