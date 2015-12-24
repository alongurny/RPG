package rpg.physics;

public final class Vector2D {

	public static final Vector2D ZERO = new Vector2D(0, 0);

	public static final Vector2D NORTH = new Vector2D(0, -1);
	public static final Vector2D SOUTH = new Vector2D(0, 1);
	public static final Vector2D WEST = new Vector2D(-1, 0);
	public static final Vector2D EAST = new Vector2D(1, 0);
	public static final Vector2D UNDEFINED = new Vector2D(Double.NaN, Double.NaN);

	private final double x;
	private final double y;

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2D multiply(double d) {
		return new Vector2D(x * d, y * d);
	}

	public Vector2D add(Vector2D w) {
		return new Vector2D(x + w.x, y + w.y);
	}

	public Vector2D negate() {
		return new Vector2D(-x, -y);
	}

	public Vector2D subtract(Vector2D v) {
		return this.add(v.negate());
	}

	public Vector2D divide(double d) {
		return new Vector2D(x / d, y / d);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public String toString() {
		return String.format("(%.4g, %.4g)", x, y);
	}

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

	public Vector2D getUnitalVector() {
		return divide(getMagnitude());
	}

	public double getMagnitude() {
		return Math.sqrt(x * x + y * y);
	}

	public boolean equals(Object o) {
		if (o instanceof Vector2D) {
			Vector2D v = (Vector2D) o;
			return x == v.x && y == v.y;
		}
		return false;
	}

	public static Vector2D valueOf(String string) {
		if (string.startsWith("(") && string.endsWith(")")) {
			string = string.substring(1, string.length() - 1);
			String[] nums = string.split(",");
			if (nums.length == 2) {
				return new Vector2D(Double.parseDouble(nums[0].trim()), Double.parseDouble(nums[1].trim()));
			}
		}
		throw new RuntimeException();
	}

}
