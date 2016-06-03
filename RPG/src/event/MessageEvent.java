package event;

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

	public MessageEvent(Message message) {
		this(message, System.currentTimeMillis());
	}

	public MessageEvent(Message message, long arrival) {
		this.message = message;
		this.arrival = arrival;
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
	 * Returns the message.
	 * 
	 * @return the message
	 */
	public Message getMessage() {
		return message;
	}

}
