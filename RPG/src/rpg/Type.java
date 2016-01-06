package rpg;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public enum Type {
	;
	private Map<String, Class<?>> params;

	private Type(Entry<String, Class<?>>... params) {
		this.params = new HashMap<>();
		for (Entry<String, Class<?>> entry : params) {
			this.params.put(entry.getKey(), entry.getValue());
		}
	}
}
