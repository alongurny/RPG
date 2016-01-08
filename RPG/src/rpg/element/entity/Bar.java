package rpg.element.entity;

import java.awt.Color;
import java.util.HashMap;
import java.util.Set;

public class Bar {

	private static java.util.Map<String, Color[]> colors = new HashMap<>();

	static {
		bindColor("health", Color.GREEN, Color.RED);
		bindColor("mana", Color.BLUE, new Color(0, 100, 255, 200));
	}

	public static Set<String> getBound() {
		return colors.keySet();
	}

	public static boolean isBound(String key) {
		return colors.containsKey(key);
	}

	public static Color[] getColors(String key) {
		return colors.get(key);
	}

	public static void bindColor(String name, Color mainColor, Color secondColor) {
		colors.put(name, new Color[] { mainColor, secondColor });
	}

}
