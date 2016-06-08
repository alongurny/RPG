package rpg.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class TileDrawer extends Drawer {

	public static Sprite sprite(int tileset, int row, int firstColumn, int lastColumn) {
		List<TileDrawer> list = new ArrayList<>();
		for (int i = firstColumn; i <= lastColumn; i++) {
			list.add(new TileDrawer(tileset, row, i));
		}
		return new Sprite(list);
	}

	private BufferedImage image;
	private int tileset;
	private int row;

	private int col;
	private int width;
	private int height;
	private int x;
	private int y;

	public TileDrawer(int tileset, int row, int col) {
		image = Tileset.get(tileset).subimage(row, col);
		this.tileset = tileset;
		this.row = row;
		this.col = col;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.x = -width / 2;
		this.y = -height / 2;
	}

	public TileDrawer(int tileset, int row, int col, int x, int y, int width, int height) {
		this.tileset = tileset;
		this.row = row;
		this.col = col;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		image = Tileset.get(tileset).subimage(row, col).getSubimage(0, 0, width, height);
	}

	public void draw(Graphics2D g) {
		g.drawImage(image, x, y, width, height, null);
	}

	@Override
	public String represent() {
		return String.format("%s %d:int %d:int %d:int %d:int %d:int %d:int %d:int", getClass().getName(), tileset, row,
				col, x, y, width, height);
	}

}
