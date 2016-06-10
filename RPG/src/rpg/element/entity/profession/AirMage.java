package rpg.element.entity.profession;

import external.Messages;
import rpg.ability.air.FlightSpell;
import rpg.ability.damage.DamageType;
import rpg.element.entity.Entity;
import rpg.graphics.Drawer;
import rpg.graphics.TileDrawer;

public class AirMage extends Mage {

	private Drawer leftDrawer;
	private Drawer rightDrawer;

	public AirMage() {
		super(new FlightSpell());
		leftDrawer = TileDrawer.sprite(Messages.getInt("AirMage.tileset"), Messages.getInt("AirMage.left.row"),
				Messages.getInt("AirMage.left.firstColumn"), Messages.getInt("AirMage.left.lastColumn"));
		rightDrawer = TileDrawer.sprite(Messages.getInt("AirMage.tileset"), Messages.getInt("AirMage.right.row"),
				Messages.getInt("AirMage.right.firstColumn"), Messages.getInt("AirMage.right.lastColumn"));
	}

	@Override
	public double getResistance(Entity entity, DamageType damageType) {
		return 0;
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
