package rpg.logic.level;

import java.util.Arrays;

import rpg.element.Portal;
import rpg.element.entity.Dragon;
import rpg.element.map.Block;
import rpg.element.map.BottomHalfBlock;

public class InterestingGame extends Game {

	private static final int ROWS = 12;
	private static final int COLUMNS = 64;

	public InterestingGame() {
		super(ROWS, COLUMNS);
		addDynamicElement(new BottomHalfBlock(getGrid().getLocation(10, 5)));
		for (int c = 0; c < COLUMNS; c++) {
			addDynamicElement(new Block(getGrid().getLocation(ROWS - 1, c)));
		}
		for (int r = 0; r < ROWS - 1; r++) {
			addDynamicElement(new Block(getGrid().getLocation(r, 0)));
		}
		for (int r = 0; r < ROWS - 1; r++) {
			addDynamicElement(new Block(getGrid().getLocation(r, COLUMNS - 1)));
		}
		addInitialLocation(getGrid().getLocation(ROWS - 2, 3));
		addInitialLocation(getGrid().getLocation(ROWS - 2, 19));
		addInitialLocation(getGrid().getLocation(ROWS - 2, 35));
		addInitialLocation(getGrid().getLocation(ROWS - 2, 51));
		addDynamicElement(new Dragon(getGrid().getLocation(ROWS - 2, 12)));
		Arrays.stream(Portal.getPair(getGrid().getLocation(ROWS - 2, 24), getGrid().getLocation(ROWS - 2, 32)))
				.forEach(this::addDynamicElement);

	}

}
