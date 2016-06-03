package rpg.element.entity;

import java.util.Arrays;
import java.util.List;

import rpg.Thing;
import rpg.ability.Ability;
import rpg.element.DamageType;
import rpg.element.Entity;

public abstract class Profession extends Thing {

	public static final FireMage FIRE_MAGE = new FireMage();
	public static final FrostMage FROST_MAGE = new FrostMage();
	public static final NoProfession NONE = new NoProfession();

	private List<Ability> abilities;

	protected Profession(Ability... abilities) {
		this.abilities = Arrays.asList(abilities);
	}

	public List<Ability> getAbilities() {
		return abilities;
	}

	public abstract double getResistance(Entity entity, DamageType type);

	public abstract double getMaxMana(Entity entity);

}
