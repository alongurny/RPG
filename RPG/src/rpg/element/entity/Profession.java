package rpg.element.entity;

import rpg.Thing;

public abstract class Profession extends Thing {

	public Profession() {
		init();
	}

	public double getDefaultMana(Player player) {
		return Math.max(0, Thing.getModifier(player.getInteger("intelligence")) * 4);
	}

	protected abstract void init();

	public static final Profession MAGE = new Profession() {

		@Override
		protected void init() {
		}
	};

	public static final Profession WARRIOR = new Profession() {
		@Override
		protected void init() {

		}
	};

}
