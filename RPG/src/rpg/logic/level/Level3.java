package rpg.logic.level;

import rpg.element.Block;

public class Level3 extends Game {

	private static final int ROWS = 8;
	private static final int COLUMNS = 64;

	public Level3() {
		super(ROWS, COLUMNS);
		for (int c = 0; c < COLUMNS; c++) {
			addDynamicElement(new Block(getGrid().getLocation(ROWS - 1, c)));
		}
		for (int r = 0; r < ROWS - 1; r++) {
			addDynamicElement(new Block(getGrid().getLocation(r, 0)));
		}
		addInitialLocation(getGrid().getLocation(ROWS - 2, 3));
	}

}
