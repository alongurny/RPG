package rpg;

import rpg.element.Entity;

public interface Cost {
	void cost(Entity entity);

	public static Cost bar(String key, double value) {
		return entity -> entity.remove(key, value);
	}
}
