package rpg.element.entity;

import rpg.ability.HasteSpell;
import rpg.ability.MagicMissileSpell;
import rpg.element.DamageType;
import rpg.element.Entity;

public class ForceMage extends Profession {

	public ForceMage() {
		super(new HasteSpell(), new MagicMissileSpell());
	}

	@Override
	public double getMaxMana(Entity entity) {
		return 40 + entity.getAttribute(Attribute.INT);
	}

	@Override
	public double getResistance(Entity entity, DamageType type) {
		return type == DamageType.FORCE ? entity.getRank() * 3 : 0;
	}

}