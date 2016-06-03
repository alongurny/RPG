package rpg.element.entity;

import rpg.ability.fire.FireballSpell;
import rpg.ability.fire.LavaSpell;
import rpg.element.DamageType;
import rpg.element.Entity;

public class FireMage extends Profession {

	public FireMage() {
		super(new FireballSpell(), new LavaSpell());
	}

	@Override
	public double getMaxMana(Entity entity) {
		return 40 + entity.getAttribute(Attribute.INT);
	}

	@Override
	public double getResistance(Entity entity, DamageType type) {
		return type == DamageType.COLD ? entity.getRank() * 3 : 0;
	}

}