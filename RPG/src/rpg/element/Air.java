package rpg.element;

import java.awt.Color;
import java.awt.Graphics;

import rpg.level.Level;

public class Air extends StaticElement {

	public Air(int y, int x) {
		super(y, x);
	}

	@Override
	public void update(Level level) {

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
		g.fillRect(0, 0, 32, 32);
	}

	@Override
	public int getIndex() {
		return 0;
	}

}
