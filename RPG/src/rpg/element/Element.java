package rpg.element;

import java.util.HashMap;
import java.util.Map;

import rpg.element.entity.AttributeSet;
import rpg.logic.Level;
import rpg.physics.Vector2D;
import rpg.ui.Drawable;
import rpg.ui.Rectangle;

public abstract class Element implements Drawable {

	private Vector2D location;
	protected AttributeSet basicAttributes;

	public Element(Vector2D location) {
		this.location = location;
		this.basicAttributes = new AttributeSet();
	}

	public double getContinuous(String name) {
		return basicAttributes.getContinuous(name);
	}

	public int getDiscrete(String name) {
		return basicAttributes.getDiscrete(name);
	}

	public boolean getBoolean(String name) {
		return basicAttributes.getBoolean(name);
	}

	public Vector2D getVector(String name) {
		return basicAttributes.getVector(name);
	}

	public void setContinuous(String name, double value) {
		basicAttributes.setContinuous(name, value);
	}

	public void setDiscrete(String name, int value) {
		basicAttributes.setDiscrete(name, value);
	}

	public void setBoolean(String name, boolean value) {
		basicAttributes.setBoolean(name, value);
	}

	public void setVector(String name, Vector2D value) {
		basicAttributes.setVector(name, value);
	}

	public AttributeSet getAttributes() {
		return basicAttributes;
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
		return new Rectangle(location.getX() + rel.getX(), location.getY() + rel.getY(), rel.getWidth(),
				rel.getHeight());
	}

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

	public abstract void update(Level level, double d);

	public abstract void onCollision(Level level, Element other);

	public abstract boolean isPassable(Level level, Element other);

	@Override
	public String toString() {
		return String.format("%s[location=%s]", getClass().getName(), getLocation());
	}

}
