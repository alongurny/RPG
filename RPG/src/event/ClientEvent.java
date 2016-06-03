package event;

/**
 * This class represents an event that happened to a client.
 * 
 * @author Alon
 *
 */
public class ClientEvent {

	private long time;

	/**
	 * Creates a new ClientEvent with the current time in milliseconds as time.
	 */
	public ClientEvent() {
		this(System.currentTimeMillis());
	}

	/**
	 * Creates a new ClientEvent with the given time in milliseconds.
	 * 
	 * @param time
	 *            The time in milliseconds
	 */
	public ClientEvent(long time) {
		this.time = time;
	}

	/**
	 * Returns the time of this event.
	 * 
	 * @return The time of this event
	 */
	public long getTime() {
		return time;
	}
}
