package rpg.element.bonus;

import rpg.element.entity.Entity;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.TileDrawer;
import rpg.logic.level.Game;

public class HealthPotion extends Bonus {

	private static final Drawer sprite = new TileDrawer(0, 25, 2);

	public HealthPotion(Vector2D location) {
		super(location);
	}

	@Override
	public Drawer getDrawer() {
		return sprite;
	}

	@Override
	public int getIndex() {
		return 1;
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-16, -16, 32, 32);
	}

	@Override
	public void update(Game game, double dt) {

	}

	@Override
	protected void onPick(Entity picker) {
		picker.addHealth(20);
	}

}
