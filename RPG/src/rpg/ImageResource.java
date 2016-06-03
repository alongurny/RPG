package rpg;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.ImageIcon;

public class ImageResource implements Resource {

	private static Map<String, ImageResource> map = new HashMap<>();

	private Image image;

	public ImageResource(Image image) {
		this.image = Objects.requireNonNull(image);
	}

	public Image getImage() {
		return image;
	}

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

}
