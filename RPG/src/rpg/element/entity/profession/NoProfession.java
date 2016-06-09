package rpg.element.entity.profession;

import rpg.ability.damage.DamageType;
import rpg.element.entity.Entity;

public class NoProfession extends Profession {

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