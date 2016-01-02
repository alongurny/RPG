package rpg.exception;

public class RPGException extends RuntimeException {

	private static final long serialVersionUID = 5732044096204585856L;

	public RPGException(String message) {
		super(message);
	}

	public RPGException(Throwable t) {
		super(t);
	}

}
