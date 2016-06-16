package rpg.geometry;

/**
 * This class represents a rectangle.
 * 
 * @author Alon
 *
 */
public class Rectangle {

	/**
	 * Returns the intersection of two rectangle.
	 * 
	 * @param a
	 *            a rectangle
	 * @param b
	 *            another rectangle
	 * @return the intersection rectangle
	 */
	public static Rectangle intersect(Rectangle a, Rectangle b) {
		double x1 = Math.max(a.getMinX(), b.getMinX());
		double y1 = Math.max(a.getMinY(), b.getMinY());
		double x2 = Math.min(a.getMaxX(), b.getMaxX());
		double y2 = Math.min(a.getMaxY(), b.getMaxY());
		return new Rectangle(x1, y1, x2 - x1, y2 - y1);
	}

	private double x, y;

	private double width, height;

	/**
	 * Constructs a new rectangle such that its upper-left corner is (x, y) and
	 * its dimensions are (width, height).
	 * 
	 * @param x
	 *            left coordinate
	 * @param y
	 *            top coordinate
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
	public Rectangle(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Returns <code>true</code> if the given point is contained in this
	 * rectangle.
	 * 
	 * @param point
	 *            a point
	 * @return whether the point is contained in this rectangle
	 */
	public boolean contains(Vector2D point) {
		return getMinX() <= point.getX() && point.getX() <= getMaxX() && getMinY() <= point.getY()
				&& point.getY() <= getMaxY();
	}

	/**
	 * Returns the height.
	 * 
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	private double getMaxX() {
		return getX() + getWidth();
	}

	private double getMaxY() {
		return getY() + getHeight();
	}

	private double getMinX() {
		return getX();
	}

	private double getMinY() {
		return getY();
	}

	/**
	 * Returns the width.
	 * 
	 * @return the width.
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * Returns the x-coordinate.
	 * 
	 * @return the x-coordinate
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns the y-coordinate.
	 * 
	 * @return the y-coordinate
	 */
	public double getY() {
		return y;
	}

	/**
	 * Returns <code>true</code> if the rectangles intersect, and
	 * <code>false</code> otherwise.
	 * 
	 * @param r
	 *            another rectangle
	 * @return <code>true</code> if the rectangles intersect, and
	 *         <code>false</code> otherwise
	 */
	public boolean intersects(Rectangle r) {
		double tw = this.width + this.x;
		double th = this.height + this.y;
		double rw = r.width + r.x;
		double rh = r.height + r.y;
		double tx = this.x;
		double ty = this.y;
		double rx = r.x;
		double ry = r.y;
		return rw > tx && rh > ty && tw > rx && th > ry;
	}

	@Override
	public String toString() {
		return String.format("Rectangle[x: %s, y: %s, width: %s, height: %s]", x, y, width, height);
	}

}
