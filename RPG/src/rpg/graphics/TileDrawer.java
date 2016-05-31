package rpg.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import rpg.graphics.draw.Drawer;

public class TileDrawer extends Drawer {

	private BufferedImage image;

	private int tileset;
	private int row;
	private int col;

	public TileDrawer(int tileset, int row, int col) {
		image = Tileset.get(tileset).subimage(row, col);
		this.tileset = tileset;
		this.row = row;
		this.col = col;
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
		g.drawImage(image, -image.getWidth() / 2, -image.getHeight() / 2, image.getWidth(), image.getHeight(), null);
	}

	@Override
	public String represent() {
		return String.format("TileDrawer %d:int %d:int %d:int", tileset, row, col);
	}

}
