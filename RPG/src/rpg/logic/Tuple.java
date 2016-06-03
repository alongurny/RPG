package rpg.logic;

import java.util.function.Function;

public class Tuple<A, B> {

	public static <A, B> Tuple<A, B> of(A a, B b) {
		return new Tuple<A, B>(a, b);
	}

	private A first;
	private B second;

	private Tuple(A first, B second) {
		this.first = first;
		this.second = second;
	}

	public A getFirst() {
		return first;
	}

	public B getSecond() {
		return second;
	}

	public <C> C match(Function<A, Function<B, C>> f) {
		return f.apply(getFirst()).apply(getSecond());
	}

}
