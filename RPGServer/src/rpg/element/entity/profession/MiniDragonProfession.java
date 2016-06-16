package rpg.element.entity.profession;

import external.Messages;
import rpg.ability.damage.DamageType;
import rpg.ability.fire.FireballSpell;
import rpg.element.entity.Attribute;
import rpg.element.entity.Entity;
import rpg.graphics.DrawIcon;
import rpg.graphics.Drawer;

public class MiniDragonProfession extends Profession {

	private Drawer leftDrawer;
	private Drawer rightDrawer;

	public MiniDragonProfession() {
		super(new FireballSpell());
		String path = Messages.getString("DragonRace.img");
		leftDrawer = new DrawIcon(path, -66, 50);
		rightDrawer = new DrawIcon(path, 66, 50);
	}

	@Override
	public double getMaxMana(Entity entity) {
		return getAttribute(Attribute.INT) * 10;
	}

	@Override
	public double getMaxHealth(Entity entity) {
		return getAttribute(Attribute.CON) * 10;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Returned value is very high if damageType is {@link DamageType#FIRE FIRE}
	 * , and 0 otherwise.
	 * </p>
	 */
	@Override
	public double getResistance(Entity entity, DamageType type) {
		return type == DamageType.FIRE ? 2 : 0;
	}

	@Override
	public int getAttribute(Attribute attr) {
		switch (attr) {
		case CON:
			return 2;
		case INT:
			return 3;
		default:
			return 0;
		}
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
