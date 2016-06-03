package rpg.element;

import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.TileDrawer;
import rpg.logic.level.Level;

public class Block extends Element {

	private Drawer sprite;

	public Block(Vector2D location) {
		super(location);
		sprite = new TileDrawer(0, 13, 23);

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
	public Rectangle getRelativeRect() {
		return new Rectangle(-16, -16, 32, 32);
	}

	@Override
	public boolean isPassable(Level level, Element other) {
		return false;
	}

	@Override
	public void onCollision(Level level, Element other) {

	}

	@Override
	public void update(Level level, double dt) {

	}

}
