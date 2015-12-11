package rpg.element;

import java.awt.Rectangle;

import rpg.physics.Vector2D;
import rpg.ui.Drawable;

public abstract class DynamicElement extends Element implements Drawable {

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

	@Override
	public String toString() {
		return String.format("%s[location=%s]", getClass().getName(), getLocation());
	}
}
