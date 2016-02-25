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
		DOUBLE(Double.class), INTEGER(Integer.class), BOOLEAN(Boolean.class), VECTOR(Vector2D.class), STRING(
				String.class);

		private Class<?> enclosingType;

		private KeyType(Class<?> enclosingType) {
			this.enclosingType = enclosingType;
		}

		public Class<?> getEnclosingType() {
			return enclosingType;
		}

		public String getName() {
			return toString().toLowerCase();
		}
	}

	private static List<Class<?>> enclosingTypes = Arrays.asList(Double.class, Integer.class, Boolean.class,
			Vector2D.class, String.class);

	public static Object readObject(String value) {
		String[] brr = value.split(":");
		switch (brr[1].trim()) {
		case "number":
			return Double.valueOf(brr[0].trim());
		case "integer":
			return Integer.valueOf(brr[0].trim());
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
	private Map<String, Double> maxima;
	private Map<String, Double> minima;

	public Thing() {
		this.attributes = new HashMap<>();
		this.maxima = new HashMap<>();
		this.minima = new HashMap<>();
	}

	public void setLimited(String key, double value) {
		setLimited(key, value, 0, value);
	}

	public void setLimited(String key, double value, double minimum, double maximum) {
		setMinimum(key, minimum);
		setMaximum(key, maximum);
		set(key, value);
	}

	public void setMinimum(String key, double minimum) {
		minima.put(key, minimum);
		if (hasKey(key)) {
			set(key, getDouble(key));
		}
	}

	public void setMaximum(String key, double maximum) {
		maxima.put(key, maximum);
		if (hasKey(key)) {
			set(key, getDouble(key));
		}
	}

	public void add(String key, double value) {
		set(key, getDouble(key) + value);
	}

	public void remove(String key, double value) {
		set(key, getDouble(key) - value);
	}

	public void set(String key, Object value) {
		if (!enclosingTypes.contains(value.getClass())) {
			throw new RPGException("Type not allowed");
		}
		if (value instanceof Number) {
			double doubleValue = ((Number) value).doubleValue();
			if (minima.containsKey(key)) {
				doubleValue = Math.max(getMinimum(key), doubleValue);
			}
			if (maxima.containsKey(key)) {
				doubleValue = Math.min(getMaximum(key), doubleValue);
			}
			value = doubleValue;
		}
		attributes.put(key, value);
	}

	public KeyType getType(String key) {
		switch (attributes.get(key).getClass().getSimpleName()) {
		case "Double":
			return KeyType.DOUBLE;
		case "Boolean":
			return KeyType.BOOLEAN;
		case "Vector2D":
			return KeyType.VECTOR;
		case "String":
			return KeyType.STRING;
		default:
			throw new RPGException("");
		}
	}

	public boolean hasKey(String key) {
		return attributes.containsKey(key);
	}

	public Set<String> getKeys() {
		return attributes.keySet();
	}

	public double getDouble(String key) {
		if (hasKey(key) && getType(key) == KeyType.DOUBLE)
			return (double) attributes.get(key);
		throw new RPGException("Key " + key + " does not map to a double value");

	}

	public int getInteger(String key) {
		double value = getDouble(key);
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

	public double getDouble(String key, double defaultValue) {
		return hasKey(key) ? (double) attributes.get(key) : defaultValue;
	}

	public double getMaximum(String key) {
		return maxima.get(key);
	}

	public double getMinimum(String key) {
		return minima.get(key);
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
			throw new RPGException("Key '" + key + "' not contained");
		}
		switch (getType(key)) {
		case DOUBLE:
			return getDouble(key);
		case BOOLEAN:
			return getBoolean(key);
		case VECTOR:
			return getVector(key);
		case STRING:
			return getString(key);
		default:
			throw new RPGException("");
		}
	}

	public static int getModifier(int value) {
		if (value % 2 != 0) {
			value--;
		}
		return (value - 10) / 2;
	}

}
