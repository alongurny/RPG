package rpg.graphics;

import java.awt.Color;
import java.awt.Graphics2D;

public class SetColor extends Drawer {

	private Color color;

	public SetColor(Color color) {
		this.color = color;
	}

	public SetColor(int rgb) {
		this(new Color(rgb, true));
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
	}

	@Override
	protected String represent() {
		return String.format("%s %d:int", getClass().getName(), color.getRGB());
	}
}
