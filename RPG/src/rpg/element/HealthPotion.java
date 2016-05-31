package rpg.element;

import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.TileDrawer;
import rpg.graphics.draw.Drawer;
import rpg.logic.level.Level;

public class HealthPotion extends Bonus {

	private static final Drawer sprite = TileDrawer.tile(0, 25, 2);

	public HealthPotion(Vector2D location) {
		super(location);
	}

	@Override
	protected void onPick(Entity picker) {
		picker.add("health", 20);
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-16, -16, 32, 32);
	}

	@Override
	public int getIndex() {
		return 1;
	}

	@Override
	public void update(Level level, double dt) {

	}

	@Override
	public Drawer getDrawer() {
		return sprite;
	}

}
