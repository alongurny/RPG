package external;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import rpg.graphics.Drawer;
import rpg.graphics.Sprite;
import rpg.graphics.TileDrawer;

/**
 * This class is used to externalize <code>String</code>s and integers.
 * 
 * @author Alon
 *
 */
public class Messages {
	private static final String BUNDLE_NAME = "external.messages";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	public static int getInt(String key) {
		try {
			return Integer.parseInt(RESOURCE_BUNDLE.getString(key));
		} catch (MissingResourceException e) {
			throw new RuntimeException(e);
		}
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			throw new RuntimeException(e);
		}
	}

	public static TileDrawer getTileDrawer(String name) {
		String[] s = getString(name).split(",");
		return new TileDrawer(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]));
	}

	public static Sprite getSprite(String name) {
		String[] s = getString(name).split(",");
		return TileDrawer.sprite(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]),
				Integer.parseInt(s[3]));
	}

	public static Drawer getTileDrawer(String name, int x, int y, int width, int height) {
		String[] s = getString(name).split(",");
		return new TileDrawer(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), x, y, width,
				height);
	}
}
