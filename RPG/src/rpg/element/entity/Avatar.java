package rpg.element.entity;

import rpg.ability.damage.DamageType;
import rpg.ability.fire.FireballSpell;
import rpg.ability.fire.LavaSpell;
import rpg.ability.force.FlightSpell;
import rpg.ability.force.HasteSpell;
import rpg.ability.force.MagicMissileSpell;
import rpg.ability.frost.IceBlockSpell;
import rpg.element.entity.profession.Profession;

public class Avatar extends Profession {

	public Avatar() {
		super(new FlightSpell(), new FireballSpell(), new LavaSpell(), new HasteSpell(), new MagicMissileSpell(),
				new IceBlockSpell());
	}

	@Override
	public double getMaxMana(Entity entity) {
		return 100;
	}

	@Override
	public double getResistance(Entity entity, DamageType type) {
		return 0;
	}

}