package rpg.geometry;

public final class Vector3D {

	public static final Vector3D ZERO = new Vector3D(0, 0, 0);

	public static final Vector3D NORTH = new Vector3D(0, -1, 0);
	public static final Vector3D SOUTH = new Vector3D(0, 1, 0);
	public static final Vector3D WEST = new Vector3D(-1, 0, 0);
	public static final Vector3D EAST = new Vector3D(1, 0, 0);
	public static final Vector3D UNDEFINED = new Vector3D(Double.NaN, Double.NaN, Double.NaN);

	public static Vector3D sum(Vector3D... vectors) {
		if (vectors.length == 0) {
			throw new IllegalArgumentException("Must have at least one vector");
		}
		Vector3D res = vectors[0];
		for (int i = 1; i < vectors.length; i++) {
			res = res.add(vectors[i]);
		}
		return res;
	}
	private final double x;
	private final double y;

	private final double z;

	public Vector3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3D add(Vector3D w) {
		return new Vector3D(x + w.x, y + w.y, z + w.z);
	}

	public Vector3D divide(double d) {
		return new Vector3D(x / d, y / d, z / d);
	}

	public boolean equals(Object o) {
		if (o instanceof Vector3D) {
			Vector3D v = (Vector3D) o;
			return x == v.x && y == v.y && z == v.z;
		}
		return false;
	}

	public double getMagnitude() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	public Vector3D getUnitalVector() {
		return divide(getMagnitude());
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public Vector3D multiply(double d) {
		return new Vector3D(x * d, y * d, z * d);
	}

	public Vector3D negate() {
		return new Vector3D(-x, -y, -z);
	}

	public Vector3D subtract(Vector3D v) {
		return this.add(v.negate());
	}

	@Override
	public String toString() {
		return String.format("(%.4g, %.4g, %.4g) ", x, y, z);
	}

}
