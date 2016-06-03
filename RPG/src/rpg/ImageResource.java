package rpg;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.ImageIcon;

/**
 * This class provides utilities to use {@link Image Images} and
 * {@link BufferedImage Buffered Images}.
 * 
 * @author Alon
 *
 */
public class ImageResource implements Resource {

	private static Map<String, ImageResource> map = new HashMap<>();

	public static ImageResource get(String path) {
		if (!map.containsKey(path)) {
			try {
				ImageResource resource = new ImageResource(new ImageIcon(new File(path).toURI().toURL()).getImage());
				map.put(path, resource);
				return resource;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map.get(path);
	}

	private Image image;

	public ImageResource(Image image) {
		this.image = Objects.requireNonNull(image);
	}

	public Image getImage() {
		return image;
	}

}
