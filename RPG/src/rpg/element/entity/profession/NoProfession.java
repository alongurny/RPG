package rpg.element.entity.profession;

import rpg.ability.damage.DamageType;
import rpg.element.entity.Entity;

/**
 * A profession that has no abilities, no resistances and no mana.
 * 
 * @author Alon
 */
public class NoProfession extends Profession {
	/**
	 * 
	 * Constructs a new instance of this class. Different instances of
	 * {@link Entity} should NOT reference the same instance.
	 * 
	 */
	public NoProfession() {
		super();
	}

	@Override
	public double getMaxMana(Entity entity) {
		return 0;
	}

	@Override
	public double getResistance(Entity entity, DamageType type) {
		return 0;
	}

}