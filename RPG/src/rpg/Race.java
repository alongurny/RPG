package rpg;

public class Race {

	public static final Race HUMAN = new Race(new AttributeSet(0, 0, 0), 42);
	public static final Race DRAGON = new Race(new AttributeSet(20, 20, 20), 50);

	private double defaultHealth;
	private AttributeSet attributeSet;

	public Race(AttributeSet attributeSet, double defaultHealth) {
		this.attributeSet = attributeSet;
		this.defaultHealth = defaultHealth;
	}

	public AttributeSet getAttributeSet() {
		return attributeSet;
	}

	public double getDefaultHealth() {
		return defaultHealth;
	}

}
