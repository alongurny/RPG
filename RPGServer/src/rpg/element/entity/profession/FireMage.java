package rpg.element.entity.profession;

import rpg.ability.damage.DamageType;
import rpg.ability.fire.FireballSpell;
import rpg.ability.fire.LavaSpell;
import rpg.element.entity.Entity;
import rpg.graphics.Drawer;
import rpg.graphics.TileDrawer;

public class FireMage extends Mage {

	public FireMage() {
		super(new FireballSpell(), new LavaSpell());
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Returned value is positive if damageType is {@link DamageType#FIRE FIRE},
	 * and 0 otherwise.
	 * </p>
	 */
	@Override
	public double getResistance(Entity entity, DamageType type) {
		return type == DamageType.COLD ? entity.getRank() * 3 : 0;
	}

	@Override
	public Drawer getLeftDrawer() {
		return TileDrawer.sprite(1, 1, 0, 2);
	}

	@Override
	public Drawer getRightDrawer() {
		return TileDrawer.sprite(1, 2, 0, 2);
	}

}