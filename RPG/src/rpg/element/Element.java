package rpg.element;

import rpg.Mechanism;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.logic.level.Game;

/**
 * This class represents an element of the game. An element is defined as
 * something that has a location and can be seen.
 * 
 * @author Alon
 *
 */
public abstract class Element implements Mechanism {

	public static double distance(Element a, Element b) {
		return Vector2D.distance(a.getLocation(), b.getLocation());
	}

	private Vector2D location;

	/**
	 * Creates a new element in the given location. Note that since this class
	 * is abstract, this constructor cannot be called directly.
	 * 
	 * @param location
	 *            the location of this element
	 */
	public Element(Vector2D location) {
		this.location = location;
	}

	public Rectangle getAbsoluteRect() {
		Rectangle rel = getRelativeRect();
		Vector2D location = getLocation();
		return new Rectangle(location.getX() + rel.getX(), location.getY() + rel.getY(), rel.getWidth(),
				rel.getHeight());
	}

	public abstract Drawer getDrawer();

	public abstract int getIndex();

	/**
	 * 
	 * @return the location of this element
	 */
	public Vector2D getLocation() {
		return location;
	}

	public abstract Rectangle getRelativeRect();

	public abstract boolean isPassable(Game game, Element other);

	public abstract void onCollision(Game game, Element other);

	/**
	 * Sets a new location to this element
	 * 
	 * @param location
	 *            the new location
	 */
	public void setLocation(Vector2D location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return String.format("%s[location=%s]", getClass().getName(), getLocation());
	}

}
