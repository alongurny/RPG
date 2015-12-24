package event;

import tcp.chat.message.Message;

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

	public Message getMessage() {
		return message;
	}

	public long getArrival() {
		return arrival;
	}

}