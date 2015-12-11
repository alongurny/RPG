package rpg;

import java.awt.Color;
import java.awt.Graphics;

import rpg.ui.Drawable;

public class AbilityDrawer implements Drawable {
	private AbilityHandler handler;
	private int x, y;

	public AbilityDrawer(int x, int y, AbilityHandler handler) {
		this.handler = handler;
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, 100, 100);

	}

	@Override
	public int getIndex() {
		return 0;
	}

}
