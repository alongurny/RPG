package rpg.element;

import java.awt.Graphics;

import rpg.element.entity.Entity;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Sprite;
import rpg.graphics.Tileset;
import rpg.logic.level.Level;

public class ManaPotion extends Bonus {

	private static final Sprite sprite = Sprite.get(Tileset.get(0), 24, 29);

	public ManaPotion(Vector2D location) {
		super(location);
	}

	@Override
	public void draw(Graphics g) {
		sprite.draw(g);
	}

	@Override
	protected void onPick(Entity picker) {
		picker.addBarValue("mana", 20);
	}

	@Override
	public Rectangle getRelativeRect() {
		return sprite.getRelativeRect();
	}

	@Override
	public int getIndex() {
		return 1;
	}

	@Override
	public void update(Level level, double dt) {

	}

}
