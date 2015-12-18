package rpg.element.entity;

import java.awt.Color;
import java.util.HashMap;

public class Bar {

	private static java.util.Map<String, Color[]> colors;

	static {
		colors = new HashMap<>();
		bindColor("health", Color.GREEN, Color.RED);
		bindColor("mana", Color.BLUE, new Color(0, 100, 255, 200));
	}

	public static void bindColor(String name, Color mainColor, Color secondColor) {
		colors.put(name, new Color[] { mainColor, secondColor });
	}

	private double value, maximum;

	public Bar(double maximum) {
		this(maximum, maximum);
	}

	public Bar(double value, double maximum) {
		this.value = value;
		this.maximum = maximum;
	}

	public double getValue() {
		return value;
	}

	public void addValue(double dvalue) {
		setValue(value + dvalue);
	}

	public void setValue(double value) {
		this.value = Math.min(value, maximum);
	}

	public double getMaximum() {
		return maximum;
	}

	public void addMaximum(double dmaximum) {
		setMaximum(maximum + dmaximum);
	}

	public void setMaximum(double maximum) {
		this.maximum = maximum;
		this.value = Math.min(value, maximum);
	}

	public static boolean isBound(String key) {
		return colors.containsKey(key);
	}

	public static Color[] getColors(String key) {
		return colors.get(key);
	}
}
