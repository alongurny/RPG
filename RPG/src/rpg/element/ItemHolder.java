package rpg.element;

import rpg.Interactive;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.item.Item;
import rpg.logic.level.Game;

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
	public boolean isInteractable(Game game, Entity entity) {
		return getAbsoluteRect().intersects(entity.getAbsoluteRect());
	}

	@Override
	public boolean isPassable(Game game, Element other) {
		return true;
	}

	@Override
	public void onCollision(Game game, Element other) {

	}

	@Override
	public void onInteract(Game game, Entity other) {
		other.getInventory().add(item);
		game.removeDynamicElement(this);
	}

	@Override
	public void update(Game game, double dt) {

	}

}
