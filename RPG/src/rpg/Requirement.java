package rpg;

import rpg.element.entity.Entity;

public abstract class Requirement extends Thing {

	public static class BarRequirement extends Requirement {

		public BarRequirement(String name, double value) {
			set("name", name);
			set("value", value);
		}

		@Override
		public boolean isRequireable(Entity entity) {
			return entity.getBarValue(getString("name"), 0) >= getDouble("value");
		}

		@Override
		public void require(Entity entity) {
			entity.removeBarValue(getString("name"), getDouble("value"));
		}
	}

	public static class AliveRequirement extends Requirement {

		@Override
		public boolean isRequireable(Entity entity) {
			return entity.isAlive();
		}

		@Override
		public void require(Entity entity) {

		}
	}

	public abstract boolean isRequireable(Entity entity);

	public abstract void require(Entity entity);

}
