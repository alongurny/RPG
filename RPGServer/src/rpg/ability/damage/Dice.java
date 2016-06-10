package rpg.ability.damage;

import java.util.Random;

/**
 * Dice are used to introduced an effect of randomness to the game.
 * 
 * @author Alon
 *
 */
public class Dice {

	/**
	 * Constructs a new dice with the given number of sides
	 * 
	 * @param sides
	 *            the number of sides
	 * @return a new dice
	 */
	public static Dice get(int sides) {
		return new Dice(sides);
	}

	private int max;
	private int lastValue;

	private Random random;

	private Dice(int max) {
		random = new Random();
		this.max = max;
	}

	/**
	 * Returns the last value rolled by this dice.
	 * 
	 * @return the last rolled value
	 */
	public int getLastValue() {
		return lastValue;
	}

	/**
	 * Rolls the dice an returns its value.
	 * 
	 * @return the value of the dice after rolling
	 */
	public int roll() {
		lastValue = random.nextInt(max) + 1;
		return lastValue;
	}

}
