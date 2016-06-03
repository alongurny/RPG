package rpg.element;

import rpg.Interactive;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.item.Item;
import rpg.logic.level.Level;

public class ItemHolder extends Element implements Interactive {

	private Item item;

	public ItemHolder(Vector2D location, Item item) {
		super(location);
		this.item = item;
	}

	@Override
	public Drawer getDrawer() {
		return item.getDrawer();
	}

	@Override
	public int getIndex() {
		return 3;
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-16, -16, 32, 32);
	}

	@Override
	public boolean isInteractable(Level level, Entity entity) {
		return getAbsoluteRect().intersects(entity.getAbsoluteRect());
	}

	@Override
	public boolean isPassable(Level level, Element other) {
		return true;
	}

	@Override
	public void onCollision(Level level, Element other) {

	}

	@Override
	public void onInteract(Level level, Entity other) {
		other.getInventory().add(item);
		level.removeDynamicElement(this);
	}

	@Override
	public void update(Level level, double dt) {

	}

}
