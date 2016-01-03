package rpg.element;

import java.awt.Graphics;

import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Sprite;
import rpg.graphics.Tileset;
import rpg.logic.level.Level;

public class Block extends Element {

	private static final Sprite sprite = Sprite.get(Tileset.get(0), 13, 23);

	public Block(Vector2D location) {
		super(location);
	}

	@Override
	public void draw(Graphics g) {
		sprite.draw(g);
	}

	@Override
	public int getIndex() {
		return 100;
	}

	@Override
	public void update(Level level, double dt) {

	}

	@Override
	public boolean isPassable(Level level, Element other) {
		return false;
	}

	@Override
	public Rectangle getRelativeRect() {
		return sprite.getRelativeRect();
	}

	@Override
	public void onCollision(Level level, Element other) {

	}

}
