package event;

/**
 * An interface to listen to client events.
 * 
 * @author Alon
 *
 */
public interface ClientListener {

	/**
	 * 
	 * This method is called when the client event is triggered.
	 * 
	 * @param e
	 *            The client event
	 */
	void onClientConnected(ClientEvent e);
}
