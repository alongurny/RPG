package rpg.geometry;

public class Rectangle {

	private double x, y;
	private double width, height;

	public Rectangle(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public double getMinX() {
		return getX();
	}

	public double getMinY() {
		return getY();
	}

	public double getMaxX() {
		return getX() + getWidth();
	}

	public double getMaxY() {
		return getY() + getHeight();
	}

	@Override
	public String toString() {
		return String.format("Rectangle {x: %s, y: %s, width: %s, height: %s", x, y, width, height);
	}

	public boolean intersects(Rectangle r) {
		double tw = this.width;
		double th = this.height;
		double rw = r.width;
		double rh = r.height;
		if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
			return false;
		}
		double tx = this.x;
		double ty = this.y;
		double rx = r.x;
		double ry = r.y;
		rw += rx;
		rh += ry;
		tw += tx;
		th += ty;
		return ((rw < rx || rw > tx) && (rh < ry || rh > ty) && (tw < tx || tw > rx) && (th < ty || th > ry));
	}

	public static Rectangle intersect(Rectangle a, Rectangle b) {
		double x1 = Math.max(a.getMinX(), b.getMinX());
		double y1 = Math.max(a.getMinY(), b.getMinY());
		double x2 = Math.min(a.getMaxX(), b.getMaxX());
		double y2 = Math.min(a.getMaxY(), b.getMaxY());
		return new Rectangle(x1, y1, x2 - x1, y2 - y1);
	}

	public boolean contains(Vector2D v) {
		return getMinX() <= v.getX() && v.getX() <= getMaxX() && getMinY() <= v.getY() && v.getY() <= getMaxY();
	}

}
