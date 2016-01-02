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
			return entity.getBarValue(getString("name"), 0) >= getNumber("value");
		}

		@Override
		public void require(Entity entity) {
			entity.removeBarValue(getString("name"), getNumber("value"));
		}
	}

	public abstract boolean isRequireable(Entity entity);

	public abstract void require(Entity entity);

}
