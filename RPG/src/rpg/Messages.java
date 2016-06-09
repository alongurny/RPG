package rpg;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME = "rpg.messages";

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
}
