package rpg;

public class AttributeSet {

	private int strength, dexterity, intelligence;

	public AttributeSet(int strength, int dexterity, int intelligence) {
		this.strength = strength;
		this.dexterity = dexterity;
		this.intelligence = intelligence;
	}

	public int getStrength() {
		return strength;
	}

	public int getStrengthModifier() {
		return getModifier(strength);
	}

	public int getDexterity() {
		return dexterity;
	}

	public int getDexterityModifier() {
		return getModifier(dexterity);
	}

	public int getIntelligence() {
		return intelligence;
	}

	public int getIntelligenceModifier() {
		return getModifier(intelligence);
	}

	public static int getModifier(int ability) {
		if (ability % 2 != 0) {
			ability--;
		}
		return (ability - 10) / 2;
	}

	public AttributeSet add(AttributeSet bonus) {
		return new AttributeSet(strength + bonus.strength, dexterity + bonus.dexterity, intelligence + bonus.intelligence);
	}

}
