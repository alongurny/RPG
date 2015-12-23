package rpg.element;

import java.awt.Graphics;

import rpg.Interactive;
import rpg.element.entity.Entity;
import rpg.item.Item;
import rpg.logic.Level;
import rpg.physics.Vector2D;
import rpg.ui.Rectangle;

public class ItemHolder extends Element implements Interactive {

	private Item item;

	public ItemHolder(Vector2D location, Item item) {
		super(location);
		this.item = item;
	}

	@Override
	public void draw(Graphics g) {
		item.draw(g);
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-16, -16, 32, 32);
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
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onInteract(Level level, Entity other) {
		other.getInventory().add(item);
		level.removeDynamicElement(this);
	}

	@Override
	public boolean isInteractable(Level level, Entity entity) {
		return getAbsoluteRect().intersects(entity.getAbsoluteRect());
	}

}
