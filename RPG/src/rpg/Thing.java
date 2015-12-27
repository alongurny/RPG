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
import rpg.physics.Vector2D;

public abstract class Thing {

	public static final List<Class<?>> TYPES = Arrays.asList(Double.class, Integer.class, Boolean.class,
			String.class, Vector2D.class);

	public static Object readObject(String value) {
		String[] brr = value.split(":");
		switch (brr[1].trim()) {
		case "double":
			return Double.valueOf(brr[0].trim());
		case "int":
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

	public Thing() {
		this.attributes = new HashMap<>();
	}

	public void set(String key, Object value) {
		if (!TYPES.contains(value.getClass())) {
			throw new RPGException("Type not allowed");
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

	public double getContinuous(String key) {
		return (double) get(key);
	}

	public int getDiscrete(String key) {
		return (int) get(key);
	}

	public boolean getBoolean(String key) {
		return (boolean) get(key);
	}

	public Vector2D getVector(String key) {
		return (Vector2D) get(key);
	}

	public String getString(String key) {
		return (String) get(key);
	}

	public double getContinuous(String key, double defaultValue) {
		return (double) get(key, defaultValue);
	}

	public int getDiscrete(String key, int defaultValue) {
		return (int) get(key, defaultValue);
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return (boolean) get(key, defaultValue);
	}

	public Vector2D getVector(String key, Vector2D defaultValue) {
		return (Vector2D) get(key, defaultValue);
	}

	public String getString(String key, String defaultValue) {
		return (String) get(key, defaultValue);
	}

	public Object get(String key) {
		if (!hasKey(key)) {
			throw new RPGException("no key " + key);
		}
		return attributes.get(key);
	}

	public Object get(String key, Object defaultValue) {
		return attributes.getOrDefault(key, defaultValue);
	}

	public static int getModifier(int value) {
		if (value % 2 != 0) {
			value--;
		}
		return (value - 10) / 2;
	}

}
