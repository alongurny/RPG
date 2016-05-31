package rpg.element;

import rpg.Interactive;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.TileDrawer;
import rpg.graphics.draw.Drawer;
import rpg.item.MasterKey;
import rpg.logic.level.Level;

public class Door extends Element implements Interactive {

	private static int width = 32, height = 32;
	private Drawer openDrawer;
	private Drawer closedDrawer;

	public Door(Vector2D location) {
		super(location);
		set("open", false);
		openDrawer = TileDrawer.tile(0, 11, 27);
		closedDrawer = TileDrawer.tile(0, 11, 23);
	}

	public boolean isOpen() {
		return getBoolean("open");
	}

	@Override
	public Drawer getDrawer() {
		return isOpen() ? openDrawer : closedDrawer;
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-width / 2, -height / 2, width, height);
	}

	@Override
	public int getIndex() {
		return 3;
	}

	@Override
	public void update(Level level, double dt) {

	}

	@Override
	public void onCollision(Level level, Element other) {

	}

	@Override
	public boolean isPassable(Level level, Element other) {
		return isOpen();
	}

	@Override
	public boolean isInteractable(Level level, Entity other) {
		for (int i = 0; i < other.getInventory().size(); i++) {
			if (other.getInventory().get(i) instanceof MasterKey) {
				if (Element.distance(other, this) < 36) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onInteract(Level level, Entity other) {
		for (int i = 0; i < other.getInventory().size(); i++) {
			if (other.getInventory().get(i) instanceof MasterKey) {
				set("open", true);
				other.getInventory().remove(i);
				return;
			}
		}
	}

}
