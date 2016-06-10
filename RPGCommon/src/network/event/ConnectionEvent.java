package network.event;

import network.TcpClient;

/**
 * This class represents a connection or disconnection of a client from the
 * server.
 * 
 * @author Alon
 *
 */
public class ConnectionEvent {

	private long time;
	private TcpClient client;

	/**
	 * Creates a new ConnectionEvent with the current time in milliseconds as
	 * time.
	 */
	public ConnectionEvent(TcpClient client) {
		this(client, System.currentTimeMillis());
	}

	/**
	 * Creates a new ConnectionEvent with the given time in milliseconds.
	 * 
	 * @param time
	 *            The time in milliseconds
	 */
	public ConnectionEvent(TcpClient client, long time) {
		this.client = client;
		this.time = time;
	}

	public TcpClient getClient() {
		return client;
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
