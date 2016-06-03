package rpg.element.entity;

import rpg.Thing;
import rpg.element.Entity;

public abstract class Race extends Thing {

	public static final Race HUMAN = new Race() {

		@Override
		public double getMaxHealth(Entity entity) {
			return 4 * entity.getAttribute(Attribute.CON);
		}

		@Override
		public double getMaxMana(Entity entity) {
			return 0;
		}

		@Override
		public int getAttribute(Attribute attr) {
			return 0;
		}

		public double getSpeed(Entity entity) {
			return 64;
		}
	};
	public static final Race DRAGON = new Race() {
		@Override
		public double getMaxHealth(Entity entity) {
			return 100;
		}

		@Override
		public double getMaxMana(Entity entity) {
			return 100;
		}

		@Override
		public int getAttribute(Attribute attr) {
			return 10;
		}

		public double getSpeed(Entity entity) {
			return 20;
		}
	};

	public abstract double getMaxMana(Entity entity);

	public abstract double getMaxHealth(Entity entity);

	public abstract int getAttribute(Attribute attr);

	public abstract double getSpeed(Entity entity);

}
