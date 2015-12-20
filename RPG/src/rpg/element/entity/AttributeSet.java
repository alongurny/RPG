package rpg.element.entity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AttributeSet {

	private Map<String, Double> continuousAttributes;
	private Map<String, Integer> discreteAttributes;
	private Map<String, Boolean> booleanAttributes;

	public AttributeSet() {
		continuousAttributes = new HashMap<>();
		discreteAttributes = new HashMap<>();
		booleanAttributes = new HashMap<>();
	}

	public void setContinuous(String name, double value) {
		continuousAttributes.put(name, value);
	}

	public void setDiscrete(String name, int value) {
		discreteAttributes.put(name, value);
	}

	public void setBoolean(String name, boolean value) {
		booleanAttributes.put(name, value);
	}

	public boolean hasContinuous(String name) {
		return continuousAttributes.containsKey(name);
	}

	public boolean hasDiscrete(String name) {
		return discreteAttributes.containsKey(name);
	}

	public boolean hasBoolean(String name) {
		return booleanAttributes.containsKey(name);
	}

	public double getContinuous(String name, double defaultValue) {
		Double value = continuousAttributes.get(name);
		return value != null ? value : defaultValue;
	}

	public int getDiscrete(String name, int defaultValue) {
		Integer value = discreteAttributes.get(name);
		return value != null ? value : defaultValue;
	}

	public boolean getBoolean(String name, boolean defaultValue) {
		Boolean value = booleanAttributes.get(name);
		return value != null ? value : defaultValue;
	}

	public double getContinuous(String name) {
		return getContinuous(name, 0.0);
	}

	public int getDiscrete(String name) {
		return getDiscrete(name, 0);
	}

	public boolean getBoolean(String name) {
		return getBoolean(name, false);
	}

	public static int getModifier(int value) {
		if (value % 2 != 0) {
			value--;
		}
		return (value - 10) / 2;
	}

	public static AttributeSet read(String fileName) {
		AttributeSet set = new AttributeSet();
		try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = in.readLine()) != null) {
				String[] arr = line.split(":");
				String[] brr = arr[0].split("=");
				switch (arr[1].trim()) {
				case "continuous":
					set.setContinuous(brr[0].trim(), Double.valueOf(brr[1].trim()));
					break;
				case "discrete":
					set.setDiscrete(brr[0].trim(), Integer.valueOf(brr[1].trim()));
					break;
				case "boolean":
					set.setBoolean(brr[0].trim(), Boolean.valueOf(brr[1].trim()));
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return set;
	}

}
