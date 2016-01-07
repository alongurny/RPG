package rpg;

public class Attribute {
	private String key;
	private Class<?> type;

	public Attribute(Class<?> type, String key) {
		this.key = key;
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public Class<?> getType() {
		return type;
	}

}
