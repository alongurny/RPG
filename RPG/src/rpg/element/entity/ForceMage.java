package rpg.element.entity;

import rpg.ability.force.HasteSpell;
import rpg.ability.force.MagicMissileSpell;
import rpg.element.DamageType;
import rpg.element.Entity;

public class ForceMage extends Mage {

	public ForceMage() {
		super(new HasteSpell(), new MagicMissileSpell());
	}

	@Override
	public double getResistance(Entity entity, DamageType type) {
		return type == DamageType.FORCE ? entity.getRank() * 3 : 0;
	}

}