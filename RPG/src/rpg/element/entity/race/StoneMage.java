package rpg.element.entity.race;

import rpg.ability.damage.DamageType;
import rpg.ability.fire.FireballSpell;
import rpg.ability.fire.LavaSpell;
import rpg.element.entity.Entity;
import rpg.element.entity.profession.Mage;

public class StoneMage extends Mage {

	public StoneMage() {
		super(new FireballSpell(), new LavaSpell());
	}

	@Override
	public double getResistance(Entity entity, DamageType type) {
		return 0;
	}

}