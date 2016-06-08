package event;

import tcp.TcpClient;
import tcp.message.Message;

/**
 * This class represents an event regarding {@link Message}s. When a message
 * arrives, this event is fired.
 * 
 * @author Alon
 *
 */
public class MessageEvent {

	private Message message;
	private long arrival;
	private TcpClient client;

	public MessageEvent(TcpClient client, Message message) {
		this(client, message, System.currentTimeMillis());
	}

	public MessageEvent(TcpClient client, Message message, long arrival) {
		this.message = message;
		this.arrival = arrival;
		this.client = client;
	}

	/**
	 * Returns the time of arrival of the message.
	 * 
	 * @return the time of arrival of the message
	 */
	public long getArrival() {
		return arrival;
	}

	public TcpClient getClient() {
		return client;
	}

	/**
	 * Returns the message.
	 * 
	 * @return the message
	 */
	public Message getMessage() {
		return message;
	}

}
