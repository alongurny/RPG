package rpg.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class StretchedTileDrawer extends Drawer {

	public static Sprite sprite(int tileset, int row, int firstColumn, int lastColumn) {
		List<StretchedTileDrawer> list = new ArrayList<>();
		for (int i = firstColumn; i <= lastColumn; i++) {
			list.add(new StretchedTileDrawer(tileset, row, i));
		}
		return new Sprite(list);
	}

	private BufferedImage image;
	private int tileset;
	private int row;

	private int col;
	private int width;
	private int height;

	public StretchedTileDrawer(int tileset, int row, int col) {
		image = Tileset.get(tileset).getTile(row, col);
		this.tileset = tileset;
		this.row = row;
		this.col = col;
		this.width = image.getWidth();
		this.height = image.getHeight();
	}

	public StretchedTileDrawer(int tileset, int row, int col, int width, int height) {
		this.tileset = tileset;
		this.row = row;
		this.col = col;
		this.width = width;
		this.height = height;
		image = Tileset.get(tileset).getTile(row, col);
	}

	public void draw(Graphics2D g) {
		g.drawImage(image, -width / 2, -height / 2, width, height, null);
	}

	@Override
	public String represent() {
		return String.format("%s %d:int %d:int %d:int %d:int %d:int ", getClass().getName(), tileset, row, col, width,
				height);
	}

}
