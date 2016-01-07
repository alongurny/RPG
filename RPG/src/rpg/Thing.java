package rpg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rpg.element.entity.Attribute;
import rpg.exception.RPGException;
import rpg.geometry.Vector2D;

public abstract class Thing {

	public enum KeyType {
		NUMBER(Double.class), INTEGER(Integer.class), BOOLEAN(Boolean.class), VECTOR(Vector2D.class), STRING(
				String.class);

		private Class<?> enclosingType;

		private KeyType(Class<?> enclosingType) {
			this.enclosingType = enclosingType;
		}

		public Class<?> getEnclosingType() {
			return enclosingType;
		}
	}

	private static List<Class<?>> enclosingTypes = Arrays.asList(Double.class, Integer.class, Boolean.class,
			Vector2D.class, String.class);

	public static Object readObject(String value) {
		String[] brr = value.split(":");
		switch (brr[1].trim()) {
		case "number":
			return Double.valueOf(brr[0].trim());
		case "boolean":
			return Boolean.valueOf(brr[0].trim());
		case "vector":
			return Vector2D.valueOf(brr[0].trim());
		case "string":
			return brr[0].trim();
		default:
			throw new RuntimeException("No match for " + value);
		}
	}

	public static List<Attribute> read(String fileName) {
		List<Attribute> set = new ArrayList<>();
		try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = in.readLine()) != null) {
				String[] arr = line.split("=");
				set.add(new Attribute(arr[0].trim(), readObject(arr[1])));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return set;
	}

	private Map<String, Object> attributes;

	public Thing() {
		this.attributes = new HashMap<>();
	}

	public void set(String key, Object value) {
		if (!enclosingTypes.contains(value.getClass())) {
			throw new RPGException("Type not allowed");
		}
		if (value instanceof Number) {
			value = ((Number) value).doubleValue();
		}
		attributes.put(key, value);
	}

	public Class<?> getType(String key) {
		return attributes.get(key).getClass();
	}

	public boolean hasKey(String key) {
		return attributes.containsKey(key);
	}

	public Set<String> getKeys() {
		return attributes.keySet();
	}

	public double getNumber(String key) {
		return (double) attributes.get(key);
	}

	public int getInteger(String key) {
		double value = getNumber(key);
		if (value % 1 == 0) {
			return (int) value;
		}
		throw new RPGException("Double cannot be cast to an integer");
	}

	public boolean getBoolean(String key) {
		return (boolean) attributes.get(key);
	}

	public Vector2D getVector(String key) {
		return (Vector2D) attributes.get(key);
	}

	public String getString(String key) {
		return (String) attributes.get(key);
	}

	public double getNumber(String key, double defaultValue) {
		return hasKey(key) ? (double) attributes.get(key) : defaultValue;
	}

	public int getInteger(String key, int defaultValue) {
		return hasKey(key) ? getInteger(key) : defaultValue;
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return hasKey(key) ? (boolean) attributes.get(key) : defaultValue;
	}

	public Vector2D getVector(String key, Vector2D defaultValue) {
		return hasKey(key) ? (Vector2D) attributes.get(key) : defaultValue;
	}

	public String getString(String key, String defaultValue) {
		return hasKey(key) ? (String) attributes.get(key) : defaultValue;
	}

	public Object get(String key) {
		if (!hasKey(key)) {
			return null;
		}
		switch (getType(key).getSimpleName()) {
		case "Double":
			return getNumber(key);
		case "Boolean":
			return getBoolean(key);
		case "Vector2D":
			return getVector(key);
		case "String":
			return getString(key);
		default:
			return null;
		}
	}

	public static int getModifier(int value) {
		if (value % 2 != 0) {
			value--;
		}
		return (value - 10) / 2;
	}

}
