package rpg.data;

import java.util.Optional;
import java.util.function.Supplier;

public class Lazy<T> {

	private Optional<T> value;
	private Supplier<T> initializer;

	public Lazy(Supplier<T> initializer) {
		this.initializer = initializer;
		this.value = Optional.empty();
	}

	public T get() {
		if (!value.isPresent()) {
			value = Optional.of(initializer.get());
		}
		return value.get();
	}

	public boolean hasValue() {
		return value.isPresent();
	}

}
