package event;

/**
 * This class represents a connection or disconnection of a client from the
 * server.
 * 
 * @author Alon
 *
 */
public class ConnectionEvent {

	private long time;

	/**
	 * Creates a new ConnectionEvent with the current time in milliseconds as
	 * time.
	 */
	public ConnectionEvent() {
		this(System.currentTimeMillis());
	}

	/**
	 * Creates a new ConnectionEvent with the given time in milliseconds.
	 * 
	 * @param time
	 *            The time in milliseconds
	 */
	public ConnectionEvent(long time) {
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
