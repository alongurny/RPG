package rpg.graphics;

import java.awt.Graphics2D;

public class DrawString extends Drawer {

	private String string;
	private int x;
	private int y;

	public DrawString(String string, int x, int y) {
		this.string = string;
		this.x = x;
		this.y = y;
	}

	@Override
	protected String represent() {
		return String.format("%s %s:java.lang.String %d:int %d:int", getClass().getName(), string, x, y);
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawString(string, x, y);
	}

}
