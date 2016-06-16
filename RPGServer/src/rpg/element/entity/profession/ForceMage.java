package rpg.element.entity.profession;

import external.Messages;
import rpg.ability.damage.DamageType;
import rpg.ability.force.HasteSpell;
import rpg.ability.force.MagicMissileSpell;
import rpg.element.entity.Entity;
import rpg.graphics.Drawer;

public class ForceMage extends Mage {

	private Drawer leftDrawer;
	private Drawer rightDrawer;

	public ForceMage() {
		super(new HasteSpell(), new MagicMissileSpell());
		leftDrawer = Messages.getSprite("ForceMage.left");
		rightDrawer = Messages.getSprite("ForceMage.right");
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Returned value is positive if damageType is {@link DamageType#FORCE
	 * FORCE}, and 0 otherwise.
	 * </p>
	 */
	@Override
	public double getResistance(Entity entity, DamageType type) {
		return type == DamageType.FORCE ? entity.getRank() * 3 : 0;
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