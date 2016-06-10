package network.event;

/**
 * An interface to listen to message events.
 * 
 * @author Alon
 *
 */
public interface MessageListener {

	/**
	 * 
	 * This method is called when a client receives a message.
	 * 
	 * @param e
	 *            The client event
	 */
	void onReceive(MessageEvent e);
}
