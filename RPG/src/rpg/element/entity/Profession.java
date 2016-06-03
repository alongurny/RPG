package rpg.element.entity;

import java.util.Arrays;
import java.util.List;

import rpg.ability.Ability;
import rpg.element.DamageType;
import rpg.element.Entity;

public abstract class Profession {

	public static final FireMage FIRE_MAGE = new FireMage();
	public static final FrostMage FROST_MAGE = new FrostMage();
	public static final DragonProfession DRAGON = new DragonProfession();
	public static final NoProfession NONE = new NoProfession();

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
