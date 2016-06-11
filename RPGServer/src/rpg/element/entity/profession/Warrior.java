package rpg.element.entity.profession;

import external.Messages;
import rpg.ability.damage.DamageType;
import rpg.element.entity.Attribute;
import rpg.element.entity.Entity;
import rpg.graphics.Drawer;
import rpg.graphics.TileDrawer;

public class Warrior extends Profession {

	private Drawer leftDrawer;
	private Drawer rightDrawer;

	public Warrior() {
		super();
		leftDrawer = TileDrawer.sprite(Messages.getInt("Warrior.tileset"), Messages.getInt("Warrior.left.row"),
				Messages.getInt("Warrior.left.firstColumn"), Messages.getInt("Warrior.left.lastColumn"));
		rightDrawer = TileDrawer.sprite(Messages.getInt("Warrior.tileset"), Messages.getInt("Warrior.right.row"),
				Messages.getInt("Warrior.right.firstColumn"), Messages.getInt("Warrior.right.lastColumn"));
	}

	@Override
	public double getResistance(Entity entity, DamageType damageType) {
		return damageType == DamageType.PHYSICAL ? 0.05 * entity.getAttribute(Attribute.CON) : 0.1;
	}

	@Override
	public int getAttribute(Attribute attr) {
		switch (attr) {
		case CON:
			return 2;
		case DEX:
			return -2;
		case INT:
			return -2;
		case STR:
			return 6;
		default:
			throw new NullPointerException();
		}
	}

	@Override
	public double getMaxHealth(Entity entity) {
		return entity.getAttribute(Attribute.STR) * 4;
	}

	@Override
	public double getMaxMana(Entity entity) {
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
