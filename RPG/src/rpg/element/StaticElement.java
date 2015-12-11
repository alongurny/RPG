package rpg.element;

import rpg.Map;
import rpg.level.Level;
import rpg.physics.Vector2D;
import rpg.ui.Drawable;

public abstract class StaticElement extends Element implements Drawable {

	private int y, x;

	public StaticElement(int y, int x) {
		this.y = y;
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public void onCollision(Level level, Element other) {
	}

	public static Vector2D getLocation(Level level, int y, int x) {
		Map map = level.getMap();
		int height = map.getRowHeight();
		int width = map.getColumnWidth();
		return new Vector2D(x * height + height / 2, y * width + width / 2);
	}

	@Override
	public String toString() {
		return String.format("%s (%d, %d)", super.toString(), getX(), getY());
	}
}
