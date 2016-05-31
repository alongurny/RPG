package rpg;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.imageio.ImageIO;

public class ImageResource implements Resource {

	private static Map<String, ImageResource> map = new HashMap<>();

	private BufferedImage image;

	public ImageResource(BufferedImage image) {
		this.image = Objects.requireNonNull(image);
	}

	public BufferedImage getImage() {
		return image;
	}

	public static ImageResource get(String path) {
		if (!map.containsKey(path)) {
			try {
				ImageResource resource = new ImageResource(ImageIO.read(new File(path).toURI().toURL()));
				map.put(path, resource);
				return resource;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map.get(path);
	}

}
