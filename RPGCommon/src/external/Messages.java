package external;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

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
}
