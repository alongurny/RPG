package rpg.element.entity;

public class Race {

	public static final Race HUMAN = new Race(AttributeSet.read("race/human.race"));
	public static final Race DRAGON = new Race(AttributeSet.read("race/dragon.race"));

	private AttributeSet attributeSet;

	public Race(AttributeSet attributeSet) {
		this.attributeSet = attributeSet;
	}

	public AttributeSet getAttributeSet() {
		return attributeSet;
	}

}
