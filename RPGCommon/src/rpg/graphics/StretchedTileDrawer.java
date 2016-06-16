package rpg.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import rpg.data.Lazy;

public class StretchedTileDrawer extends Drawer {

	private Lazy<BufferedImage> image;
	private int tileset;
	private int row;

	private int col;
	private int width;
	private int height;

	public StretchedTileDrawer(int tileset, int row, int col, int width, int height) {
		this.tileset = tileset;
		this.row = row;
		this.col = col;
		this.width = width;
		this.height = height;
		image = new Lazy<>(Tileset.get(tileset).getTile(row, col));
	}

	public void draw(Graphics2D g) {
		g.drawImage(image.get(), -width / 2, -height / 2, width, height, null);
	}

	@Override
	public String represent() {
		return String.format("%s %d:int %d:int %d:int %d:int %d:int ", getClass().getName(), tileset, row, col, width,
				height);
	}

}
