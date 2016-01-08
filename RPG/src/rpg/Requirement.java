package rpg;

import rpg.element.entity.Entity;

public interface Requirement {
	boolean isRequireable(Entity entity);

	public static Requirement atLeast(String key, double minimum) {
		return entity -> entity.getDouble(key) >= minimum;
	}

	public static Requirement moreThan(String key, double bound) {
		return entity -> entity.getDouble(key) > bound;
	}
}
