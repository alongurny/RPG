package rpg.exception;

public class IllegalActionException extends RPGException {

	private static final long serialVersionUID = -594947572828841554L;

	public IllegalActionException(String action) {
		super("Cannot perform the action '" + action + "'");
	}
}
