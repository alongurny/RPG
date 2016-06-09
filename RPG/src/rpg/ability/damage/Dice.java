package rpg.ability.damage;

import java.util.Random;

public class Dice {

	public static Dice get(int max) {
		return new Dice(max);
	}

	private int max;
	private int lastValue;

	private Random random;

	private Dice(int max) {
		random = new Random();
		this.max = max;
	}

	public int getLastValue() {
		return lastValue;
	}

	public int roll() {
		lastValue = random.nextInt(max) + 1;
		return lastValue;
	}

}
