package rpg.element.entity;

import rpg.ability.frost.IceBlockSpell;
import rpg.element.DamageType;
import rpg.element.Entity;

public class FrostMage extends Profession {

	public FrostMage() {
		super(new IceBlockSpell());
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