package rpg.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import rpg.geometry.Rectangle;
import rpg.graphics.draw.Redrawable;

public class Sprite implements Redrawable {

	private BufferedImage[] images;
	private int index;

	public Sprite(int tileset, int row, int column) {
		this(tileset, row, column, column);
	}

	public Sprite(int tileset, int row, int firstColumn, int lastColumn) {
		images = new BufferedImage[lastColumn - firstColumn + 1];
		for (int i = 0; i < images.length; i++) {
			images[i] = Tileset.get(tileset).subimage(row, firstColumn + i);
		}
	}

	public void progress() {
		index = (index + 1) % images.length;
	}

	public void draw(Graphics g) {
		int width = images[index].getWidth();
		int height = images[index].getHeight();
		g.drawImage(images[index], -width / 2, -height / 2, width, height, null);
	}

	public BufferedImage get(int index) {
		return images[index];
	}

	public int getCount() {
		return images.length;
	}

	public Rectangle getRelativeRect(int index) {
		int width = images[index].getWidth();
		int height = images[index].getHeight();
		return new Rectangle(-width / 2, -height / 2, width, height);
	}

}
