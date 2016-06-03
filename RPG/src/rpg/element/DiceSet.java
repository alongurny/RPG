package rpg.element;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DiceSet {

	public static DiceSet set(Dice... dice) {
		return new DiceSet(Arrays.asList(dice));
	}

	public static DiceSet repeat(int count, Dice dice) {
		return new DiceSet(Collections.nCopies(count, dice));
	}

	private List<Dice> dice;

	private DiceSet(List<Dice> dice) {
		this.dice = dice;
	}

	public int roll() {
		return dice.stream().map(Dice::roll).reduce(0, (a, b) -> a + b);
	}

	public int getLastValue() {
		return dice.stream().map(Dice::getLastValue).reduce(0, (a, b) -> a + b);
	}

}
