package external;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.imageio.ImageIO;

public class BufferedImageResource {

	private static Map<String, BufferedImageResource> map = new HashMap<>();
	private static String prefix = Messages.getString("BufferedImageResource.path");

	public static BufferedImageResource get(String path) {
		if (!map.containsKey(path)) {
			try {
				BufferedImageResource resource = new BufferedImageResource(
						ImageIO.read(new File(prefix + "/" + path).toURI().toURL()));
				map.put(path, resource);
				return resource;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map.get(path);
	}

	private BufferedImage image;

	public BufferedImageResource(BufferedImage image) {
		this.image = Objects.requireNonNull(image);
	}

	public BufferedImage getImage() {
		return image;
	}

}
