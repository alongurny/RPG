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

	public TileDrawer(int tileset, int row, int col) {
		image = Tileset.get(tileset).subimage(row, col);
		this.tileset = tileset;
		this.row = row;
		this.col = col;
	}

	public void draw(Graphics2D g) {
		g.drawImage(image, -image.getWidth() / 2, -image.getHeight() / 2, image.getWidth(), image.getHeight(), null);
	}

	@Override
	public String represent() {
		return String.format("%s %d:int %d:int %d:int", getClass().getName(), tileset, row, col);
	}

}
