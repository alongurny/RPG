package rpg.element.entity.profession;

import rpg.ability.damage.DamageType;
import rpg.ability.force.HasteSpell;
import rpg.ability.force.MagicMissileSpell;
import rpg.element.entity.Attribute;
import rpg.element.entity.Entity;

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