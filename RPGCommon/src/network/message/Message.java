package network.message;

/**
 * A message contains two primary fields: data, and a type. Messages are a way
 * to wrap the string that is sent via the network.
 * 
 * @author Alon
 *
 */
public class Message {

	public enum Type {

		/**
		 * The normal data that is sent through the network.
		 */
		NORMAL, /**
				 * Metata: should not be used by regular clients.
				 */
		METADATA
	}

	/**
	 * Creates a new message with the given type and data.
	 * 
	 * @param type
	 *            a type
	 * @param data
	 *            data
	 * @return a new message
	 */
	public static Message create(Type type, String data) {
		return new Message(type, data);
	}

	/**
	 * Creates a new message with type "normal" and data.
	 * 
	 * @param data
	 *            data
	 * @return a new message
	 */
	public static Message normal(String data) {
		return new Message(Type.NORMAL, data);
	}

	/**
	 * Creates a new message with type "metadata" and data.
	 * 
	 * @param data
	 *            data
	 * @return a new message
	 */
	public static Message metadata(String data) {
		return new Message(Type.METADATA, data);
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

	/**
	 * Returns the time at which this message was created.
	 * 
	 * @return the time at which this message was created
	 */
	public long getCreationTime() {
		return created;
	}

	/**
	 * Returns the data of the message.
	 * 
	 * @return the data of the message
	 */
	public String getData() {
		return data;
	}

	/**
	 * Returns the type of the message.
	 * 
	 * @return the type of the message
	 */
	public Type getType() {
		return type;
	}

	@Override
	public String toString() {
		return String.format("Message { type: %s, data: %s }", type, data);
	}

}
