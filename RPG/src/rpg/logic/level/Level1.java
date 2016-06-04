package rpg.logic.level;

import rpg.element.Block;
import rpg.element.Door;
import rpg.geometry.Vector2D;
import rpg.logic.Grid;

public class Level1 extends Game {

	private static final int ROWS = 20;
	private static final int COLUMNS = 20;

	public Level1() {
		super(ROWS, COLUMNS);
		addInitialLocation(new Vector2D(80, 100));
		addInitialLocation(new Vector2D(80, 300));
		addInitialLocation(new Vector2D(280, 300));
		Grid grid = getGrid();
		for (int i = 0; i < COLUMNS; i++) {
			addStaticElement(new Block(grid.getLocation(0, i)));
			addStaticElement(new Block(grid.getLocation(ROWS - 1, i)));
		}
		for (int j = 1; j < ROWS - 1; j++) {
			grid.add(new Block(grid.getLocation(j, 0)));
			if (j != 12 && j != 13) {
				addStaticElement(new Block(grid.getLocation(j, 4)));
			} else {
				addDynamicElement(new Door(grid.getLocation(j, 4)));
			}
			addStaticElement(new Block(grid.getLocation(j, COLUMNS - 1)));
		}
	}

}
