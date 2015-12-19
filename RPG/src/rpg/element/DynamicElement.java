package rpg.element;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import rpg.level.Level;
import rpg.physics.Vector2D;
import rpg.ui.Drawable;

public abstract class DynamicElement implements Drawable {

	private Vector2D location;

	public DynamicElement(Vector2D location) {
		this.location = location;
	}

	public Vector2D getLocation() {
		return location;
	}

	public void setLocation(Vector2D location) {
		this.location = location;
	}

	public abstract Rectangle getRelativeRect();

	public abstract int getIndex();

	public Rectangle getAbsoluteRect() {
		Rectangle rel = getRelativeRect();
		Vector2D location = getLocation();
		return new Rectangle((int) (location.getX() + rel.getX()), (int) (location.getY() + rel.getY()),
				(int) rel.getWidth(), (int) rel.getHeight());
	}

	private static Map<Integer, Class<? extends DynamicElement>> ids;

	static {
		ids = new HashMap<>();
		put(0, Air.class);
		put(1, Block.class);
		put(2, Portal.class);
	}

	public static void put(int id, Class<? extends DynamicElement> cls) {
		ids.put(id, cls);
	}

	public abstract void update(Level level);

	public abstract void onCollision(Level level, DynamicElement other);

	public abstract boolean isPassable(Level level, DynamicElement other);

	@Override
	public String toString() {
		return String.format("%s[location=%s]", getClass().getName(), getLocation());
	}
}
