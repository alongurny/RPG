package rpg.element;

import rpg.element.ability.Fireball;
import rpg.element.entity.Player;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawable;
import rpg.logic.level.Game;

/**
 * This class represents an element of the game. An element is defined as
 * something that has a location and can be seen.
 * 
 * @author Alon
 *
 */
public abstract class Element implements Drawable, Updatable {

	public static double distance(Element a, Element b) {
		return Vector2D.distance(a.getLocation(), b.getLocation());
	}

	private Vector2D location;

	/**
	 * Creates a new element in the given location. Note that since this class
	 * is abstract, this constructor cannot be called directly.
	 * 
	 * @param location
	 *            the initial location of this element
	 */
	protected Element(Vector2D location) {
		this.location = location;
	}

	/**
	 * Returns a bounding rectangle of this element. Bounding rectangles are
	 * used for a simple algorithm of collision detection.
	 * 
	 * @return the bounding rectangle of this element
	 * @see #getRelativeRect()
	 */
	public final Rectangle getAbsoluteRect() {
		Rectangle rel = getRelativeRect();
		Vector2D location = getLocation();
		return new Rectangle(location.getX() + rel.getX(), location.getY() + rel.getY(), rel.getWidth(),
				rel.getHeight());
	}

	/**
	 * Returns the depth at which this element will be drawn.
	 * 
	 * @return this element's depth
	 * @see Depth
	 */
	public abstract Depth getDepth();

	/**
	 * Returns the current location of this element. For most elements, this is
	 * a constant value. It is changed mostly for moving element such
	 * {@link Player}, {@link Fireball}, etc.
	 * 
	 * @return the location of this element
	 */
	public Vector2D getLocation() {
		return location;
	}

	/**
	 * Returns a bounding rectangle of this element, such that its x-y
	 * coordinates are relative to this element's position. Bounding rectangles
	 * are used for a simple algorithm of collision detection.
	 * <p/>
	 * The implementation is then used to find the absolute bounding rectangle.
	 * 
	 * @return the relative bounding rectangle
	 * @see #getAbsoluteRect()
	 */
	public abstract Rectangle getRelativeRect();

	/**
	 * Returns <code>true</code> if the given element can pass through this one.
	 * Otherwise returns false.
	 * 
	 * @param game
	 *            the game
	 * @param other
	 *            an element
	 * @return whether <code>other</code> can pass through this element
	 */
	public abstract boolean isPassable(Game game, Element other);

	/**
	 * Determines what happens when this element collides with the given
	 * element.
	 * 
	 * @param game
	 *            the game
	 * @param other
	 *            an element
	 */
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

	/**
	 * Returns a default string representation of this element in the following
	 * format: {type}[location={location}], where {type} is this element's type
	 * and {location} is its location. For example, assuming there is a class
	 * <code>Ball</code> that <code>extends Element</code>: <br/>
	 * <blockquote>Ball[location=(0.000,0.000)]</blockquote>
	 * <p/>
	 * It is highly recommended that subclasses override this method.
	 */
	@Override
	public String toString() {
		return String.format("%s[location=%s]", getClass().getName(), getLocation());
	}

}
