package rpg.element;

import rpg.element.entity.Entity;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.TileDrawer;
import rpg.item.MasterKey;
import rpg.logic.level.Game;

public class Door extends Element implements Interactive {

	private static int width = 32, height = 32;
	private Drawer openDrawer;
	private Drawer closedDrawer;
	private boolean open;

	public Door(Vector2D location) {
		super(location);
		this.open = false;
		openDrawer = new TileDrawer(0, 11, 27);
		closedDrawer = new TileDrawer(0, 11, 23);
	}

	@Override
	public Drawer getDrawer() {
		return open ? openDrawer : closedDrawer;
	}

	@Override
	public Depth getDepth() {
		return Depth.MEDIUM_HIGH;
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-width / 2, -height / 2, width, height);
	}

	@Override
	public boolean isInteractable(Game game, Entity other) {
		for (int i = 0; i < other.getInventory().size(); i++) {
			if (other.getInventory().get(i) instanceof MasterKey) {
				if (Element.distance(other, this) < 36) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isOpen() {
		return open;
	}

	@Override
	public boolean isPassable(Game game, Element other) {
		return isOpen();
	}

	@Override
	public void onCollision(Game game, Element other) {

	}

	@Override
	public void onInteract(Game game, Entity other) {
		for (int i = 0; i < other.getInventory().size(); i++) {
			if (other.getInventory().get(i) instanceof MasterKey) {
				open = true;
				other.getInventory().remove(i);
				return;
			}
		}
	}

	@Override
	public void update(Game game, double dt) {

	}

}
