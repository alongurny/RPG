package rpg.element.entity.profession;

import external.Messages;
import rpg.ability.damage.DamageType;
import rpg.ability.frost.IceBlockSpell;
import rpg.element.entity.Entity;
import rpg.graphics.Drawer;
import rpg.graphics.TileDrawer;

public class FrostMage extends Mage {

	private Drawer leftDrawer;
	private Drawer rightDrawer;

	public FrostMage() {
		super(new IceBlockSpell());
		leftDrawer = TileDrawer.sprite(Messages.getInt("FrostMage.tileset"), Messages.getInt("FrostMage.left.row"),
				Messages.getInt("FrostMage.left.firstColumn"), Messages.getInt("FrostMage.left.lastColumn"));
		rightDrawer = TileDrawer.sprite(Messages.getInt("FrostMage.tileset"), Messages.getInt("FrostMage.right.row"),
				Messages.getInt("FrostMage.right.firstColumn"), Messages.getInt("FrostMage.right.lastColumn"));
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

	@Override
	public Drawer getLeftDrawer() {
		return leftDrawer;
	}

	@Override
	public Drawer getRightDrawer() {
		return rightDrawer;
	}

}