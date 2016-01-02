package rpg.element;

import rpg.Mechanism;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.logic.level.Level;
import rpg.ui.Drawable;

public abstract class Element extends Mechanism implements Drawable {

	public Element(Vector2D location) {
		set("location", location);
	}

	public Vector2D getLocation() {
		return getVector("location");
	}

	public void setLocation(Vector2D location) {
		set("location", location);
	}

	public abstract Rectangle getRelativeRect();

	public abstract int getIndex();

	public Rectangle getAbsoluteRect() {
		Rectangle rel = getRelativeRect();
		Vector2D location = getLocation();
		return new Rectangle(location.getX() + rel.getX(), location.getY() + rel.getY(), rel.getWidth(),
				rel.getHeight());
	}

	public abstract void onCollision(Level level, Element other);

	public abstract boolean isPassable(Level level, Element other);

	@Override
	public String toString() {
		return String.format("%s[location=%s]", getClass().getName(), getLocation());
	}

	public static double distance(Element a, Element b) {
		return Vector2D.distance(a.getLocation(), b.getLocation());
	}

}
