package rpg.element.entity.profession;

import rpg.ability.damage.DamageType;
import rpg.ability.fire.FireballSpell;
import rpg.element.entity.Dragon;
import rpg.element.entity.Entity;
import rpg.element.entity.race.DragonRace;

public class DragonProfession extends Profession {

	public DragonProfession() {
		super(new FireballSpell());
	}

	/**
	 * {@inheritDoc}<br/>
	 * Is zero for dragons.
	 * 
	 * @see Dragon
	 * @see DragonRace
	 */
	@Override
	public double getMaxMana(Entity entity) {
		return 0;
	}

	/**
	 * {@inheritDoc}<br/>
	 * Returned value is very high if damageType is {@link DamageType#FIRE FIRE}
	 * , and 0 otherwise.
	 */
	@Override
	public double getResistance(Entity entity, DamageType type) {
		return type == DamageType.FIRE ? 100 : 0;
	}

}
