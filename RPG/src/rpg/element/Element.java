package rpg.element;

import java.util.HashMap;
import java.util.Map;

import rpg.level.Level;

public abstract class Element {

	private static Map<Integer, Class<? extends Element>> ids;

	static {
		ids = new HashMap<>();
		put(0, Air.class);
		put(1, Block.class);
		put(2, Portal.class);
	}

	public static void put(int id, Class<? extends Element> cls) {
		ids.put(id, cls);
	}

	public abstract void update(Level level);

	public abstract void onCollision(Level level, Element other);

	public abstract boolean isPassable(Level level, Element other);

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
