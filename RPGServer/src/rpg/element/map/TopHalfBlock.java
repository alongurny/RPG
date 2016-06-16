package rpg.element.map;

import external.Messages;
import rpg.element.Depth;
import rpg.element.Element;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.logic.level.Game;

public class TopHalfBlock extends Element {

	private Drawer sprite;

	public TopHalfBlock(Vector2D location) {
		super(location);
		sprite = Messages.getTileDrawer("Block", -16, -16, 32, 16);
	}

	@Override
	public Drawer getDrawer() {
		return sprite;
	}

	@Override
	public Depth getDepth() {
		return Depth.MEDIUM_HIGH;
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
