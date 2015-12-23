package rpg.ui;

import rpg.element.Element;
import rpg.physics.Vector2D;

public class Util {
	public static double distance(Vector2D u, Vector2D v) {
		return u.subtract(v).getMagnitude();
	}

	public static double distance(Element a, Element b) {
		return distance(a.getLocation(), b.getLocation());
	}
}
