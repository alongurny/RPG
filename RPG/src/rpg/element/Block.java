package rpg.element;

import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.TileDrawer;
import rpg.graphics.draw.Drawer;
import rpg.logic.level.Level;

public class Block extends Element {

	private Drawer sprite;

	public Block(Vector2D location) {
		super(location);
		sprite = TileDrawer.tile(0, 13, 23);

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
		return new Rectangle(-16, -16, 32, 32);
	}

	@Override
	public void onCollision(Level level, Element other) {

	}

}
