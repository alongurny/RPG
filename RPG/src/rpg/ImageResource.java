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

	private static Map<String, ImageResource> cache = new HashMap<>();

	/**
	 * Returns a new <code>ImageResource</code> using this path.
	 * 
	 * @param path
	 *            the path
	 * @return a new image resource
	 */
	public static ImageResource get(String path) {
		if (!cache.containsKey(path)) {
			try {
				ImageResource resource = new ImageResource(new ImageIcon(new File(path).toURI().toURL()).getImage());
				cache.put(path, resource);
				return resource;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return cache.get(path);
	}

	private Image image;

	private ImageResource(Image image) {
		this.image = Objects.requireNonNull(image);
	}

	/**
	 * Returns the {@link Image} instance that this resource holds.
	 * 
	 * @return an image
	 */
	public Image getImage() {
		return image;
	}

}
