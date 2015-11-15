package rpg.exception;

public class IllegalActionException extends RPGException {
	public IllegalActionException(String action) {
		super("Cannot perform the action '" + action + "'");
	}
}
