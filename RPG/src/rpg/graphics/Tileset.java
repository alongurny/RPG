package rpg.graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import rpg.BufferedImageResource;
import rpg.Messages;

public class Tileset {

	private static List<Tileset> tilesets;

	static {
		tilesets = new ArrayList<>();
		load(Messages.getString("Tileset.0")); //$NON-NLS-1$
		load(Messages.getString("Tileset.1")); //$NON-NLS-1$
	}

	public static Tileset get(int index) {
		return tilesets.get(index);
	}

	private static void load(String pathname) {
		tilesets.add(new Tileset(BufferedImageResource.get(pathname).getImage()));
	}

	private BufferedImage image;

	private Tileset(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getSubimage(int x, int y, int width, int height) {
		return image.getSubimage(x, y, width, height);
	}

	public BufferedImage getTile(int row, int column) {
		return image.getSubimage(column * 32, row * 32, 32, 32);
	}

}
