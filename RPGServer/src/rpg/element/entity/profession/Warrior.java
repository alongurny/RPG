package rpg.element.entity.profession;

import external.Messages;
import rpg.ability.damage.DamageType;
import rpg.element.entity.Attribute;
import rpg.element.entity.Entity;
import rpg.graphics.Drawer;

public class Warrior extends Profession {

	private Drawer leftDrawer;
	private Drawer rightDrawer;

	public Warrior() {
		super();
		leftDrawer = Messages.getSprite("Warrior.left");
		rightDrawer = Messages.getSprite("Warrior.right");
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
			return 0;
		case INT:
			return -4;
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
