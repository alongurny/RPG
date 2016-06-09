package rpg.element.entity.profession;

import rpg.ability.damage.DamageType;
import rpg.ability.frost.IceBlockSpell;
import rpg.element.entity.Attribute;
import rpg.element.entity.Entity;

public class FrostMage extends Profession {

	public FrostMage() {
		super(new IceBlockSpell());
	}

	@Override
	public double getMaxMana(Entity entity) {
		return 40 + entity.getAttribute(Attribute.INT);
	}

	/**
	 * {@inheritDoc}<br/>
	 * Returned value is positive if damageType is {@link DamageType#COLD COLD},
	 * and 0 otherwise.
	 */
	@Override
	public double getResistance(Entity entity, DamageType damageType) {
		return damageType == DamageType.COLD ? entity.getRank() * 3 : 0;
	}

}