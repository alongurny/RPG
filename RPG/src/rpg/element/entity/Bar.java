package rpg.element.entity;

import java.awt.Color;
import java.util.HashMap;

import rpg.Thing;

public class Bar extends Thing {

	private static java.util.Map<String, Color[]> colors;

	static {
		colors = new HashMap<>();
		bindColor("health", Color.GREEN, Color.RED);
		bindColor("mana", Color.BLUE, new Color(0, 100, 255, 200));
	}

	public static void bindColor(String name, Color mainColor, Color secondColor) {
		colors.put(name, new Color[] { mainColor, secondColor });
	}

	public Bar(double maximum) {
		this(maximum, maximum);
	}

	public Bar(double value, double maximum) {
		set("value", value);
		set("maximum", maximum);
	}

	public double getValue() {
		return getNumber("value");
	}

	public void addValue(double dvalue) {
		setValue(getNumber("value") + dvalue);
	}

	public void setValue(double value) {
		set("value", Math.min(value, getNumber("maximum")));
	}

	public double getMaximum() {
		return getNumber("maximum");
	}

	public void addMaximum(double dmaximum) {
		setMaximum(getNumber("maximum") + dmaximum);
	}

	public void setMaximum(double maximum) {
		set("maximum", maximum);
		set("value", Math.min(getNumber("value"), maximum));
	}

	public static boolean isBound(String key) {
		return colors.containsKey(key);
	}

	public static Color[] getColors(String key) {
		return colors.get(key);
	}
}
