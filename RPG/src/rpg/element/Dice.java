package rpg.element;

import java.util.Random;

public class Dice {

	private int max;

	private int lastValue;
	private Random random;

	private Dice(int max) {
		random = new Random();
		this.max = max;
	}

	public static Dice get(int max) {
		return new Dice(max);
	}

	public int roll() {
		lastValue = random.nextInt(max) + 1;
		return lastValue;
	}

	public int getLastValue() {
		return lastValue;
	}

}
