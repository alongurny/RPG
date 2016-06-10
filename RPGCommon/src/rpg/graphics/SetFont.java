package rpg.graphics;

import java.awt.Font;
import java.awt.Graphics2D;

public class SetFont extends Drawer {

	private Font font;

	public SetFont(Font font) {
		this.font = font;
	}

	public SetFont(String name, int style, int size) {
		this(new Font(name, style, size));
	}

	@Override
	public void draw(Graphics2D g) {
		g.setFont(font);
	}

	@Override
	protected String represent() {
		return String.format("%s %s:java.lang.String %d:int %d:int", getClass().getName(), font.getName(),
				font.getStyle(), font.getSize());
	}
}
