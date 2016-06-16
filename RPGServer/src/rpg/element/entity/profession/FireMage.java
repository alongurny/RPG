package rpg.element.entity.profession;

import external.Messages;
import rpg.ability.damage.DamageType;
import rpg.ability.fire.FireballSpell;
import rpg.ability.fire.LavaSpell;
import rpg.element.entity.Entity;
import rpg.graphics.Drawer;

public class FireMage extends Mage {

	private Drawer leftDrawer, rightDrawer;

	public FireMage() {
		super(new FireballSpell(), new LavaSpell());
		leftDrawer = Messages.getSprite("FireMage.left");
		rightDrawer = Messages.getSprite("FireMage.right");
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
		return leftDrawer;
	}

	@Override
	public Drawer getRightDrawer() {
		return rightDrawer;
	}

}