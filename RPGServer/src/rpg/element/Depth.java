package rpg.element;

/**
 * Used by {@link Element} to determine if the instance should be drawn above or
 * behind other objects. Elements which have higher-valued depths with higher
 * values will be drawn above elements with lower-valued depths. This class
 * provides some depths to be used.
 * 
 * @author Alon
 *
 */
public class Depth implements Comparable<Depth> {

	public static Depth valueOf(int value) {
		if (value < 0 || value > 64) {
			throw new IllegalArgumentException("value out of range");
		}
		return new Depth(value);
	}

	/**
	 * The <code>Depth</code> with the lowest possible value.
	 */
	public static final Depth BOTTOM = valueOf(0);
	public static final Depth LOW = valueOf(8);
	public static final Depth MEDIUM_LOW = valueOf(20);
	public static final Depth MEDIUM = valueOf(32);
	public static final Depth MEDIUM_HIGH = valueOf(44);
	public static final Depth HIGH = valueOf(56);
	/**
	 * The <code>Depth</code> with the highest possible value.
	 */
	public static final Depth TOP = valueOf(64);

	private int value;

	private Depth(int value) {
		this.value = value;
	}

	/**
	 * The value of this depth. Higher values means that it will be painted
	 * above others.
	 * 
	 * @return the integer value of this depth object
	 */
	public int getValue() {
		return value;
	}

	@Override
	public int compareTo(Depth o) {
		return Integer.compare(value, o.value);
	}

}
