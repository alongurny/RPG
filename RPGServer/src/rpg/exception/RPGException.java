package rpg.exception;

/**
 * A general exception type that is thrown in many places in the application.
 * This exception type <code>extends</code> {@link RuntimeException}, meaning it
 * it an unchecked exception.
 * 
 * @author Alon
 *
 */
public class RPGException extends RuntimeException {

	private static final long serialVersionUID = 5732044096204585856L;

	/**
	 * Constructs a new <code>RPGException</code> with the given message.
	 * 
	 * @param message
	 *            a detailed message
	 */
	public RPGException(String message) {
		super(message);
	}

	/**
	 * Constructs a new <code>RPGException</code> that was cause by another
	 * instance of {@link Throwable}.
	 * 
	 * @param message
	 *            a detailed message
	 */
	public RPGException(Throwable t) {
		super(t);
	}

}
