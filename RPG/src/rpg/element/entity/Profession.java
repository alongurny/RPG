package rpg.element.entity;

import rpg.Thing;
import rpg.element.Entity;

public abstract class Profession extends Thing {

	public static final Mage MAGE = new Mage();
	public static final Warrior WARRIOR = new Warrior();
	public static final NoProfession NONE = new NoProfession();

	private static class Mage extends Profession {

		@Override
		public double getMaxMana(Entity entity) {
			return entity.getModifier(Attribute.INT);
		}

	}

	private static class Warrior extends Profession {

		@Override
		public double getMaxMana(Entity entity) {
			return 0;
		}

	}

	private static class NoProfession extends Profession {

		@Override
		public double getMaxMana(Entity entity) {
			return 0;
		}

	}

	public abstract double getMaxMana(Entity entity);

}
