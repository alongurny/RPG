package rpg.element;

import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.TileDrawer;
import rpg.graphics.draw.Drawer;
import rpg.logic.level.Level;

public class ManaPotion extends Bonus {

	private static final TileDrawer drawer = TileDrawer.tile(0, 24, 29);

	public ManaPotion(Vector2D location) {
		super(location);
	}

	@Override
	protected void onPick(Entity picker) {
		picker.add("mana", 20);
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
		return drawer;
	}

}
