package rpg.element.entity;

import rpg.exception.RPGException;

public class Race {

	public static final Race HUMAN = new Race(AttributeSet.read("race/human.attr"));
	public static final Race DRAGON = new Race(AttributeSet.read("race/dragon.attr"));

	private AttributeSet attributeSet;

	public Race(AttributeSet attributeSet) {
		this.attributeSet = attributeSet;
	}

	public AttributeSet getAttributeSet() {
		return attributeSet;
	}

	public static Race valueOf(String str) {
		switch (str) {
		case "HUMAN":
			return HUMAN;
		case "DRAGON":
			return DRAGON;
		default:
			throw new RPGException("No race " + str);
		}
	}

}
