package rpg.element;

import java.awt.Color;
import java.awt.Graphics;

import rpg.logic.Level;
import rpg.physics.Vector2D;
import rpg.ui.Rectangle;

public class Air extends Element {

	public Air(Vector2D location) {
		super(location);
	}

	@Override
	public void update(Level level, double dt) {

	}

	@Override
	public void onCollision(Level level, Element other) {

	}

	@Override
	public boolean isPassable(Level level, Element other) {
		return true;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(-16, -16, 32, 32);

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
