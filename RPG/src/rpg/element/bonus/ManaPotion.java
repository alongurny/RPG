package rpg.element.bonus;

import rpg.Messages;
import rpg.element.Depth;
import rpg.element.entity.Entity;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.TileDrawer;
import rpg.logic.level.Game;

public class ManaPotion extends Bonus {

	private static final TileDrawer drawer = new TileDrawer(Messages.getInt("ManaPotion.tileset"),
			Messages.getInt("ManaPotion.row"), Messages.getInt("ManaPotion.column"));

	public ManaPotion(Vector2D location) {
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
	public void update(Game game, double dt) {

	}

	@Override
	protected void onPick(Entity picker) {
		picker.addMana(20);
	}

}
