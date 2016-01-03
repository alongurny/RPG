package rpg.element;

import java.awt.Graphics;

import rpg.element.entity.Entity;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Sprite;
import rpg.graphics.Tileset;
import rpg.logic.level.Level;

public class HealthPotion extends Bonus {

	private static final Sprite sprite = Sprite.get(Tileset.get(0), 25, 2);

	public HealthPotion(Vector2D location) {
		super(location);
	}

	@Override
	public void draw(Graphics g) {
		sprite.draw(g);
	}

	@Override
	protected void onPick(Entity picker) {
		picker.addBarValue("health", 20);
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
