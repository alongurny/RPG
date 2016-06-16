package rpg.data;

/**
 * This class represents a heterogeneous pair of two types.
 * 
 * @author Alon
 *
 * @param <A>
 *            the first type
 * @param <B>
 *            the second type
 */
public class Tuple<A, B> {

	/**
	 * Constructs a new pair.
	 * 
	 * @param a
	 *            the first entry
	 * @param b
	 *            the second entry
	 * @param <A>
	 *            the first entry's type
	 * @param <B>
	 *            the second entry's type
	 * @return a new tuple
	 */
	public static <A, B> Tuple<A, B> of(A a, B b) {
		return new Tuple<A, B>(a, b);
	}

	private A first;
	private B second;

	private Tuple(A first, B second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * Returns the first entry.
	 * 
	 * @return the first entry
	 */
	public A getFirst() {
		return first;
	}

	/**
	 * Returns the second entry.
	 * 
	 * @return the second entry
	 */
	public B getSecond() {
		return second;
	}

}
