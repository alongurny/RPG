package rpg.element;

import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Sprite;
import rpg.graphics.draw.Drawer;
import rpg.logic.level.Level;

public class IceBlock extends Element {

	private static final Sprite sprite = new Sprite(0, 16, 27);

	public IceBlock(Vector2D location, double size) {
		super(location);
		set("size", size);
	}

	@Override
	public Drawer getDrawer() {
		return sprite;
	}

	@Override
	public int getIndex() {
		return 100;
	}

	@Override
	public void update(Level level, double dt) {

	}

	@Override
	public boolean isPassable(Level level, Element other) {
		return false;
	}

	@Override
	public Rectangle getRelativeRect() {
		double size = getDouble("size");
		return new Rectangle(-size / 2, -size / 2, size, size);
	}

	@Override
	public void onCollision(Level level, Element other) {
		if (other instanceof Fireball) {
			level.removeDynamicElement(this);
		}
	}

}
