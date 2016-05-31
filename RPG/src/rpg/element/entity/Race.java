package rpg.element.entity;

import rpg.Thing;

public abstract class Race extends Thing {

	public static final Race HUMAN = new Race() {

		public double getMaxHealth() {
			return 42;
		}

		@Override
		public double getMaxMana() {
			return 0;
		}
	};
	public static final Race DRAGON = new Race() {
		public double getMaxHealth() {
			return 100;
		}

		public double getMaxMana() {
			return 100;
		}
	};

	public abstract double getMaxMana();

	public abstract double getMaxHealth();

}
