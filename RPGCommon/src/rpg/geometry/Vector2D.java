package rpg.geometry;

/**
 * This class represents a two dimensional vector. It is used to measure
 * displacements, to represent points, dimensions, etc.
 * 
 * @author Alon
 *
 */
public final class Vector2D {

	/**
	 * A vector with zero entries.
	 */
	public static final Vector2D ZERO = new Vector2D(0, 0);

	/**
	 * A vector with x=0 and y=-1.
	 */
	public static final Vector2D NORTH = new Vector2D(0, -1);

	/**
	 * A vector with x=0 and y=1.
	 */
	public static final Vector2D SOUTH = new Vector2D(0, 1);

	/**
	 * A vector with x=-1 and y=0.
	 */
	public static final Vector2D WEST = new Vector2D(-1, 0);

	/**
	 * A vector with x=1 and y=0.
	 */
	public static final Vector2D EAST = new Vector2D(1, 0);

	/**
	 * Returns the distance between two vectors. It is defined as the magnitude
	 * of their difference.
	 * 
	 * @param u
	 *            a vector
	 * @param v
	 *            another vector
	 * @return the distance between the vectors
	 */
	public static double distance(Vector2D u, Vector2D v) {
		return u.subtract(v).getMagnitude();
	}

	/**
	 * Returns a sum of an array of vectors.
	 * 
	 * @param vectors
	 *            the vectors to sum
	 * @return the sum
	 */
	public static Vector2D sum(Vector2D... vectors) {
		if (vectors.length == 0) {
			throw new IllegalArgumentException("Must have at least one vector");
		}
		Vector2D res = vectors[0];
		for (int i = 1; i < vectors.length; i++) {
			res = res.add(vectors[i]);
		}
		return res;
	}

	/**
	 * Returns a new vector whose representation is given by the string.
	 * 
	 * @param string
	 *            the representation of the vector
	 * @return a new vector
	 */
	public static Vector2D valueOf(String string) {
		if (string.startsWith("(") && string.endsWith(")")) {
			string = string.substring(1, string.length() - 1);
			String[] nums = string.split(",");
			if (nums.length == 2) {
				return new Vector2D(Double.parseDouble(nums[0].trim()), Double.parseDouble(nums[1].trim()));
			}
		}
		throw new RuntimeException(string);
	}

	private final double x;
	private final double y;

	/**
	 * Constructs a new vector with this x, y coordinates.
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 */
	public Vector2D(double x, double y) {
		if (!Double.isFinite(x) || !Double.isFinite(y)) {
			throw new ArithmeticException("Vector2D must have finite entries");
		}
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the sum of this vector and the other vector.
	 * 
	 * @param w
	 *            another vector
	 * @return the sum
	 */
	public Vector2D add(Vector2D w) {
		return new Vector2D(x + w.x, y + w.y);
	}

	/**
	 * Returns the division of this vector by a scalar factor.
	 * 
	 * @param d
	 *            scalar factor
	 * @return the division result
	 */
	public Vector2D divide(double d) {
		return new Vector2D(x / d, y / d);
	}

	public boolean equals(Object o) {
		if (o instanceof Vector2D) {
			Vector2D v = (Vector2D) o;
			return x == v.x && y == v.y;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Double.hashCode(x) ^ Double.hashCode(y);
	}

	/**
	 * Returns the magnitude (length) of this vector: the distance from the
	 * origin.
	 * 
	 * @return the magnitude of this vector
	 */
	public double getMagnitude() {
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * Returns a new vector which points in the same direction as this one but
	 * with magnitude one.
	 * 
	 * @return a new vector which points in the same direction as this one but
	 *         with magnitude one
	 */
	public Vector2D getUnitalVector() {
		return divide(getMagnitude());
	}

	/**
	 * Returns the x coordinate of this vector
	 * 
	 * @return the x coordinate of this vector
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns the y coordinate of this vector
	 * 
	 * @return the y coordinate of this vector
	 */
	public double getY() {
		return y;
	}

	/**
	 * Multiplies this vector by a scalar factor.
	 * 
	 * @param d
	 *            the scalar factor
	 * @return the multiplication result
	 */
	public Vector2D multiply(double d) {
		return new Vector2D(x * d, y * d);
	}

	/**
	 * Returns the additive inverse of this vector.
	 * 
	 * @return the negation of this vector
	 */
	public Vector2D negate() {
		return new Vector2D(-x, -y);
	}

	/**
	 * Returns the difference between this vector and another one.
	 * 
	 * @param v
	 *            another vector
	 * @return the subtraction result
	 */
	public Vector2D subtract(Vector2D v) {
		return this.add(v.negate());
	}

	/**
	 * Returns the phase of this vector. The phase is the angle with the x-axis,
	 * so it satisfies Math.tan(phase) == y / x for non-zero x.
	 * 
	 * @return the phase of this vector
	 */
	public double getPhase() {
		return Math.atan2(y, x);
	}

	public static Vector2D fromPolar(double magnitude, double phase) {
		return new Vector2D(magnitude * Math.cos(phase), magnitude * Math.sin(phase));
	}

	@Override
	public String toString() {
		return String.format("(%.4g,%.4g)", x, y);
	}

}
