package rpg.element.entity;

import rpg.Thing;

public abstract class Profession {

	public abstract void init(Player player);

	public static final Profession MAGE = new Profession() {

		@Override
		public void init(Player player) {
			player.putBar("mana", new Bar(getDefaultMana(player)));
		}

		private double getDefaultMana(Player player) {
			return Math.max(0, Thing.getModifier(player.getDiscrete("intelligence")) * 4);
		}
	};

	public static final Profession WARRIOR = new Profession() {

		@Override
		public void init(Player player) {
		}

	};

}
