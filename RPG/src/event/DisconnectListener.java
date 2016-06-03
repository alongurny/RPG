package event;

/**
 * An interface to listen to connection events.
 * 
 * @author Alon
 *
 */
public interface DisconnectListener {

	/**
	 * 
	 * This method is called when a client disconnects.
	 * 
	 * @param e
	 *            The client event
	 */
	void onDisconnect(ConnectionEvent e);
}
