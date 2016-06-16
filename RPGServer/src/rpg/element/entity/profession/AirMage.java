package rpg.element.entity.profession;

import external.Messages;
import rpg.ability.air.FlightSpell;
import rpg.ability.damage.DamageType;
import rpg.element.entity.Entity;
import rpg.graphics.Drawer;

public class AirMage extends Mage {

	private Drawer leftDrawer;
	private Drawer rightDrawer;

	public AirMage() {
		super(new FlightSpell());
		leftDrawer = Messages.getSprite("AirMage.left");
		rightDrawer = Messages.getSprite("AirMage.right");
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
