package rpg.element.ability;

import rpg.element.Depth;
import rpg.element.Element;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.StretchedTileDrawer;
import rpg.logic.level.Game;

public class IceBlock extends Element {

	private double size;

	public IceBlock(Vector2D location, double size) {
		super(location);
		this.size = size;
	}

	@Override
	public Drawer getDrawer() {
		return new StretchedTileDrawer(0, 16, 27, (int) size, (int) size);
	}

	@Override
	public Depth getDepth() {
		return Depth.TOP;
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
