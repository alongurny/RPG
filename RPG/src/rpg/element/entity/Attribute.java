package rpg.element.entity;

public enum Attribute {

	STR, DEX, INT, CON;

	public static int getModifier(int value) {
		return value > 0 ? (value - 10) / 2 : -getModifier(-value);
	}

}
