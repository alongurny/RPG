package rpg.element.entity;

import rpg.element.DamageType;
import rpg.element.Entity;

public class NoProfession extends Profession {

	public NoProfession() {
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