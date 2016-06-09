package rpg.element.map;

import rpg.element.Element;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.TileDrawer;
import rpg.logic.level.Game;

public class TopHalfBlock extends Element {

	private Drawer sprite;

	public TopHalfBlock(Vector2D location) {
		super(location);
		sprite = new TileDrawer(0, 13, 23, -16, -16, 32, 16);
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
		return new Rectangle(-16, -16, 32, 16);
	}

	@Override
	public boolean isPassable(Game game, Element other) {
		return false;
	}

	@Override
	public void onCollision(Game game, Element other) {

	}

	@Override
	public void update(Game game, double dt) {

	}

}
