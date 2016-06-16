package rpg.element.entity.profession;

import external.Messages;
import rpg.ability.damage.DamageType;
import rpg.ability.frost.IceBlockSpell;
import rpg.ability.frost.IceShardsSpell;
import rpg.element.entity.Entity;
import rpg.graphics.Drawer;

public class FrostMage extends Mage {

	private Drawer leftDrawer;
	private Drawer rightDrawer;

	public FrostMage() {
		super(new IceBlockSpell(), new IceShardsSpell());
		leftDrawer = Messages.getSprite("FrostMage.left");
		rightDrawer = Messages.getSprite("FrostMage.right");
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 
	 * Returned value is positive if damageType is {@link DamageType#COLD COLD},
	 * and 0 otherwise.
	 * </p>
	 */
	@Override
	public double getResistance(Entity entity, DamageType damageType) {
		return damageType == DamageType.COLD ? entity.getRank() * 3 : 0;
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