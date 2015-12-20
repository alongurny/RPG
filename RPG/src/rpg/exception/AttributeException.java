package rpg.exception;

public class AttributeException extends RPGException {
	public AttributeException(String name) {
		super("Has no " + name);
	}
}
