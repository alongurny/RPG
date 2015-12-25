package rpg.exception;

public class AttributeException extends RPGException {

	private static final long serialVersionUID = -5949082781857929899L;

	public AttributeException(String name) {
		super("Has no " + name);
	}
}
