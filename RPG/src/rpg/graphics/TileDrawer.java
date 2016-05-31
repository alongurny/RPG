package rpg.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import rpg.geometry.Rectangle;
import rpg.graphics.draw.Drawer;

public class TileDrawer extends Drawer {

	private BufferedImage image;

	private int tilset;
	private int row;
	private int col;

	public TileDrawer(int tileset, int row, int col) {
		image = Tileset.get(tileset).subimage(row, col);
	}

	public static TileDrawer tile(int tileset, int row, int col) {
		return new TileDrawer(tileset, row, col);
	}

	public static Sprite sprite(int tileset, int row, int firstColumn, int lastColumn) {
		List<TileDrawer> list = new ArrayList<>();
		for (int i = firstColumn; i <= lastColumn; i++) {
			list.add(new TileDrawer(tileset, row, i));
		}
		return new Sprite(list);
	}

	public void draw(Graphics g) {
		g.drawImage(image, image.getWidth(), image.getHeight(), null);
	}

	public Rectangle getRelativeRect() {
		int width = image.getWidth();
		int height = image.getHeight();
		return new Rectangle(-width / 2, -height / 2, width, height);
	}

	@Override
	public String represent() {
		return String.format("TileDrawer %d:int %d:int %d:int", tilset, row, col);
	}

}
