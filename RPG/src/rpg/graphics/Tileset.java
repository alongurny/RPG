package rpg.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Tileset {

	private BufferedImage image;

	private Tileset(BufferedImage image) {
		this.image = image;
	}

	private static List<Tileset> tilesets;

	static {
		tilesets = new ArrayList<>();
		load("img/tileset0.png");
		load("img/tileset1.png");
	}

	private static void load(String pathname) {
		try {
			tilesets.add(new Tileset(ImageIO.read(new File(pathname))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Tileset get(int index) {
		return tilesets.get(index);
	}

	public BufferedImage subimage(int row, int column) {
		return image.getSubimage(column * 32, row * 32, 32, 32);
	}

}
