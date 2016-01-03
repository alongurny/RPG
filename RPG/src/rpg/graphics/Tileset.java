package rpg.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tileset {

	private static BufferedImage tileset;
	private static final String filePath = "img/tileset.png";

	static {
		try {
			tileset = ImageIO.read(new File(filePath).toURI().toURL());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage subimage(int row, int column) {
		return tileset.getSubimage(column * 32, row * 32, 32, 32);
	}

}
