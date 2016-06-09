package network.event;

/**
 * An interface to listen to connection events.
 * 
 * @author Alon
 *
 */
public interface ConnectListener {

	/**
	 * 
	 * This method is called when a client connects.
	 * 
	 * @param e
	 *            The client event
	 */
	void onConnect(ConnectionEvent e);
}
