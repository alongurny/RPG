package rpg.element.entity.profession;

import external.Messages;
import rpg.ability.damage.DamageType;
import rpg.ability.force.HasteSpell;
import rpg.ability.force.MagicMissileSpell;
import rpg.element.entity.Entity;
import rpg.graphics.Drawer;
import rpg.graphics.TileDrawer;

public class ForceMage extends Mage {

	private Drawer leftDrawer;
	private Drawer rightDrawer;

	public ForceMage() {
		super(new HasteSpell(), new MagicMissileSpell());
		leftDrawer = TileDrawer.sprite(Messages.getInt("ForceMage.tileset"), Messages.getInt("ForceMage.left.row"),
				Messages.getInt("ForceMage.left.firstColumn"), Messages.getInt("ForceMage.left.lastColumn"));
		rightDrawer = TileDrawer.sprite(Messages.getInt("ForceMage.tileset"), Messages.getInt("ForceMage.right.row"),
				Messages.getInt("ForceMage.right.firstColumn"), Messages.getInt("ForceMage.right.lastColumn"));
	}

	/**
	 * {@inheritDoc}<br/>
	 * Returned value is positive if damageType is {@link DamageType#FORCE
	 * FORCE}, and 0 otherwise.
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