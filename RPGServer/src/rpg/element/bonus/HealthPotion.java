package rpg.element.bonus;

import external.Messages;
import rpg.element.Depth;
import rpg.element.entity.Entity;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;

public class HealthPotion extends Bonus {

	private static final Drawer drawer = Messages.getTileDrawer("HealthPotion");

	public HealthPotion(Vector2D location) {
		super(location);
	}

	@Override
	public Drawer getDrawer() {
		return drawer;
	}

	@Override
	public Depth getDepth() {
		return Depth.LOW;
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-16, -16, 32, 32);
	}

	@Override
	protected void onPick(Entity picker) {
		picker.addHealth(20);
	}

}
