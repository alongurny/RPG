package rpg.element.entity.profession;

import rpg.ability.damage.DamageType;
import rpg.ability.fire.FireballSpell;
import rpg.ability.fire.LavaSpell;
import rpg.element.entity.Entity;

public class StoneMage extends Mage {

	/**
	 * Constructs a new instance of this class. Different instances of
	 * {@link Entity} should NOT reference the same instance.
	 */
	public StoneMage() {
		super(new FireballSpell(), new LavaSpell());
	}

	@Override
	public double getResistance(Entity entity, DamageType type) {
		return 0;
	}

}