package rpg.graphics;

import java.awt.Graphics;

import rpg.graphics.draw.Drawer;

public class Translate extends Drawer {

	private int dx, dy;

	public Translate(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	@Override
	public String represent() {
		return String.format("Translate %d:int %d:int", dx, dy);
	}

	@Override
	public void draw(Graphics g) {
		g.translate(dx, dy);
	}

}
