package rpg.graphics;

import java.awt.Color;
import java.awt.Font;

public class Popup {

	private static final Font TITLE_FONT = new Font("Arial", Font.PLAIN, 16);
	private double width;
	private double height;
	private String title;
	private Color color;

	public Popup(double width, double height, Color color, String title) {
		this.width = width;
		this.height = height;
		this.color = color;
		this.title = title;
	}

	public Drawer getDrawer() {
		return new SetColor(color).andThen(new FillRect(width, height))
				.andThen(new SetColor(new Color(0, 0, 0, color.getAlpha()))).andThen(new SetFont(TITLE_FONT))
				.andThen(new DrawString(title, 10, 10));
	}
}
