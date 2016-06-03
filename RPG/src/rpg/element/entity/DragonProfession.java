package rpg.element.entity;

import rpg.ability.fire.FireballSpell;
import rpg.element.DamageType;
import rpg.element.Entity;

public class DragonProfession extends Profession {

	public DragonProfession() {
		super(new FireballSpell());
	}

	@Override
	public double getMaxMana(Entity entity) {
		return 0;
	}

	@Override
	public double getResistance(Entity entity, DamageType type) {
		return type == DamageType.FIRE ? 100 : 0;
	}

}
