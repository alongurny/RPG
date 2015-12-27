package rpg.element.entity;

import java.util.Arrays;
import java.util.List;

import rpg.exception.RPGException;
import rpg.physics.Vector2D;

public final class Attribute {

	private static final List<Class<?>> ALLOWED_TYPES = Arrays.asList(Double.class, Integer.class, Boolean.class,
			String.class, Vector2D.class);

	private String key;
	private Object value;

	public Attribute(String key, Object value) {
		setKey(key);
		setValue(value);
	}

	public String getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setValue(Object value) {
		if (value == null || !ALLOWED_TYPES.contains(value.getClass())) {
			throw new RPGException("wrong value " + value + " for attribute");
		}
		this.value = value;
	}

}
