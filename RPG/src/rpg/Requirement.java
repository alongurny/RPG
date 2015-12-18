package rpg;

import rpg.element.entity.Entity;

public abstract class Requirement {

	public static class BarRequirement extends Requirement {
		private String name;
		private double value;

		public BarRequirement(String name, double value) {
			this.name = name;
			this.value = value;
		}

		@Override
		public boolean isRequireable(Entity entity) {
			return entity.getBarValue(name, 0) >= value;
		}

		@Override
		public void require(Entity entity) {
			entity.addBarValue(-value, name);
		}
	}

	public abstract boolean isRequireable(Entity entity);

	public abstract void require(Entity entity);

}
