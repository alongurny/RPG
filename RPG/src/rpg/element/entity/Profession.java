package rpg.element.entity;

public abstract class Profession {

	public abstract void init(Player player);

	public static final Profession MAGE = new Profession() {

		@Override
		public void init(Player player) {
			player.putBar("mana", new Bar(getDefaultMana(player)));
		}

		private double getDefaultMana(Player player) {
			return Math.max(0, player.getTotalAttributeSet().getIntelligenceModifier() * 4);
		}
	};

	public static final Profession WARRIOR = new Profession() {

		@Override
		public void init(Player player) {
		}

	};

}
