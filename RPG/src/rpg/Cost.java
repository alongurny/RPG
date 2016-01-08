package rpg;

import rpg.element.entity.Entity;

public interface Cost {
	void cost(Entity entity);

	public static Cost bar(String key, double value) {
		return entity -> entity.remove(key, value);
	}
}
