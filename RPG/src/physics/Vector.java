package physics;

public final class Vector {

	public static final Vector ZERO = new Vector(0, 0, 0);

	private final double x;
	private final double y;
	private final double z;

	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector multiply(double d) {
		return new Vector(x * d, y * d, z * d);
	}

	public Vector add(Vector w) {
		return new Vector(x + w.x, y + w.y, z + w.z);
	}

	public Vector negate() {
		return new Vector(-x, -y, -z);
	}

	public Vector subtract(Vector v) {
		return this.add(v.negate());
	}

	public Vector divide(double d) {
		return new Vector(x / d, y / d, z / d);
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

	@Override
	public String toString() {
		return String.format("(%.4g, %.4g, %.4g) ", x, y, z);
	}

	public static Vector sum(Vector... vectors) {
		if (vectors.length == 0) {
			throw new IllegalArgumentException("Must have at least one vector");
		}
		Vector res = vectors[0];
		for (int i = 1; i < vectors.length; i++) {
			res = res.add(vectors[i]);
		}
		return res;
	}

}
