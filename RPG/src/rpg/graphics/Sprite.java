package rpg.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import rpg.geometry.Rectangle;

public class Sprite {

	private BufferedImage[] images;

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

	public void draw(Graphics g, int index) {
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
