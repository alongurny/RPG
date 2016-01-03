package rpg.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import rpg.geometry.Rectangle;
import rpg.ui.Drawable;

public class Sprite implements Drawable {

	private BufferedImage[] images;
	private int counter = 0;

	public Sprite(BufferedImage... images) {
		this.images = images;
	}

	public static Sprite get(Tileset tileset, int row, int... columns) {
		BufferedImage[] images = new BufferedImage[columns.length];
		for (int i = 0; i < images.length; i++) {
			images[i] = tileset.subimage(row, columns[i]);
		}
		return new Sprite(images);
	}

	public void reset() {
		counter = 0;
	}

	@Override
	public void draw(Graphics g) {
		int width = images[counter].getWidth();
		int height = images[counter].getHeight();
		g.drawImage(images[counter], -width / 2, -height / 2, width, height, null);
	}

	public void step() {
		counter = (counter + 1) / images.length;
	}

	public Rectangle getRelativeRect() {
		int width = images[counter].getWidth();
		int height = images[counter].getHeight();
		return new Rectangle(-width / 2, -height / 2, width, height);
	}

}
