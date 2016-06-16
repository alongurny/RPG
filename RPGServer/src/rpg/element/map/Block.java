package rpg.element.map;

import external.Messages;
import rpg.element.Depth;
import rpg.element.Element;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.logic.level.Game;

public class Block extends Element {

	private Drawer drawer;

	public Block(Vector2D location) {
		super(location);
		drawer = Messages.getTileDrawer("Block");

	}

	@Override
	public Drawer getDrawer() {
		return drawer;
	}

	@Override
	public Depth getDepth() {
		return Depth.MEDIUM_HIGH;
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-16, -16, 32, 32);
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
