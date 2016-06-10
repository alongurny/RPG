package rpg.ability.damage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Like {@link Dice}, dice sets are used to introduce randomness to the game.
 *
 */
public class DiceSet {

	/**
	 * Returns a new set of some copies of the given dice
	 * 
	 * @param count
	 *            the number of copies to make
	 * @param dice
	 *            the dice
	 * @return a new dice set
	 */
	public static DiceSet repeat(int count, Dice dice) {
		return new DiceSet(Collections.nCopies(count, dice));
	}

	/**
	 * Constructs a new dice set from the given dice.
	 * 
	 * @param dice
	 *            the dice
	 * @return a new dice set
	 */
	public static DiceSet set(Dice... dice) {
		return new DiceSet(Arrays.asList(dice));
	}

	private List<Dice> dice;

	private DiceSet(List<Dice> dice) {
		this.dice = dice;
	}

	/**
	 * Returns the sum of all the last values of the internal dice.
	 * 
	 * @return the sum of all the last values of the internal dice
	 */
	public int getLastValue() {
		return dice.stream().map(Dice::getLastValue).reduce(0, (a, b) -> a + b);
	}

	/**
	 * Rolls all the dice and then returns the sum of them.
	 * 
	 * @return the sum of all the values of the internal dice after rolling
	 */
	public int roll() {
		return dice.stream().map(Dice::roll).reduce(0, (a, b) -> a + b);
	}

}
