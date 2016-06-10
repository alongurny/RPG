package network.event;

import network.TcpClient;
import network.message.Message;

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

	/**
	 * Constructs a new MessageEvent.
	 * 
	 * @param client
	 *            the client that created this event
	 * @param message
	 *            the message that was sent
	 */
	public MessageEvent(TcpClient client, Message message) {
		this(client, message, System.currentTimeMillis());
	}

	/**
	 * Constructs a new MessageEvent.
	 * 
	 * @param client
	 *            the client that created this event
	 * @param message
	 *            the message that was sent
	 * @param arrival
	 *            the time of arrival
	 */
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

	/**
	 * Returns the client that created this event.
	 * 
	 * @return the client that created this event
	 */
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
