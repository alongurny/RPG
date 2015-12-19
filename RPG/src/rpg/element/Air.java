package rpg.element;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import rpg.level.Level;
import rpg.physics.Vector2D;

public class Air extends DynamicElement {

	public Air(Vector2D location) {
		super(location);
	}

	@Override
	public void update(Level level) {

	}

	@Override
	public void onCollision(Level level, DynamicElement other) {

	}

	@Override
	public boolean isPassable(Level level, DynamicElement other) {
		return true;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, 32, 32);

	}

	@Override
	public int getIndex() {
		return 0;
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-16, -16, 32, 32);
	}

}
