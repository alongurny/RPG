package rpg.element;

import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.TileDrawer;
import rpg.logic.level.Game;

public class IceBlock extends Element {

	public static final Drawer sprite = new TileDrawer(0, 16, 27);

	private double size;

	public IceBlock(Vector2D location, double size) {
		super(location);
		this.size = size;
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
		return new Rectangle(-size / 2, -size / 2, size, size);
	}

	@Override
	public boolean isPassable(Game game, Element other) {
		return false;
	}

	@Override
	public void onCollision(Game game, Element other) {
		if (other instanceof Fireball) {
			game.removeDynamicElement(this);
		}
	}

	@Override
	public void update(Game game, double dt) {

	}

}
