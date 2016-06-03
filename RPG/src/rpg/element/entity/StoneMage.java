package rpg.element.entity;

import rpg.ability.fire.FireballSpell;
import rpg.ability.fire.LavaSpell;
import rpg.element.DamageType;
import rpg.element.Entity;

public class StoneMage extends Mage {

	public StoneMage() {
		super(new FireballSpell(), new LavaSpell());
	}

	@Override
	public double getResistance(Entity entity, DamageType type) {
		return 0;
	}

}