package rpg.element;

import rpg.Mechanism;
import rpg.logic.Level;
import rpg.physics.Vector2D;
import rpg.ui.Drawable;
import rpg.ui.Rectangle;

public abstract class Element extends Mechanism implements Drawable {

	private Vector2D location;

	public Element(Vector2D location) {
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
		return new Rectangle(location.getX() + rel.getX(), location.getY() + rel.getY(), rel.getWidth(),
				rel.getHeight());
	}

	public abstract void onCollision(Level level, Element other);

	public abstract boolean isPassable(Level level, Element other);

	@Override
	public String toString() {
		return String.format("%s[location=%s]", getClass().getName(), getLocation());
	}

}
