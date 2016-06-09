package rpg.element.entity.profession;

import rpg.ability.damage.DamageType;
import rpg.ability.fire.FireballSpell;
import rpg.ability.fire.LavaSpell;
import rpg.element.entity.Attribute;
import rpg.element.entity.Entity;

public class FireMage extends Profession {

	public FireMage() {
		super(new FireballSpell(), new LavaSpell());
	}

	@Override
	public double getMaxMana(Entity entity) {
		return 40 + entity.getAttribute(Attribute.INT);
	}

	/**
	 * {@inheritDoc}<br/>
	 * Returned value is positive if damageType is {@link DamageType#FIRE FIRE},
	 * and 0 otherwise.
	 */
	@Override
	public double getResistance(Entity entity, DamageType type) {
		return type == DamageType.COLD ? entity.getRank() * 3 : 0;
	}

}