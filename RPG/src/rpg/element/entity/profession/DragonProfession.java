package rpg.element.entity.profession;

import rpg.ability.damage.DamageType;
import rpg.ability.fire.FireballSpell;
import rpg.element.entity.Entity;

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
