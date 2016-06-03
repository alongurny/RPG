package rpg.ability;

public enum TargetType {
	NONE(0b0000), SELF(0b0001), ALLY(0b0010), ENEMY(0b0100), SELF_OR_ALLY(0b0011), ALLY_OR_ENEMY(0b0110), SELF_OR_ENEMY(
			0b0101), ANY_ENTITY(0b0111), ANY_ELEMENT(0b1111);

	private int value;

	TargetType(int value) {
		this.value = value;
	}

	public boolean contains(TargetType other) {
		return (value & other.value) != 0 && value > other.value;
	}
}
