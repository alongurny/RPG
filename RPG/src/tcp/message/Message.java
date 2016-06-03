package tcp.message;

public class Message {

	public enum Type {
		DATA, METADATA
	}

	public static Message create( Type type, String data) {
		return new Message(type, data);
	}
	public static Message data(String text) {
		return new Message(Type.DATA, text);
	}
	public static Message metadata(String metadata) {
		return new Message(Type.METADATA, metadata);
	}

	private String data;

	private long created;

	private Type type;

	private Message(Type type, String data) {
		this(type, data, System.currentTimeMillis());
	}

	private Message(Type type, String data, long created) {
		this.type = type;
		this.data = data;
		this.created = created;
	}

	public long getCreationTime() {
		return created;
	}

	public String getData() {
		return data;
	}

	public Type getType() {
		return type;
	}

	@Override
	public String toString() {
		return String.format("Message { type: %s, data: %s }", type, data);
	}

}
