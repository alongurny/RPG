package rpg.logic.level;

import rpg.element.Door;
import rpg.element.ItemHolder;
import rpg.element.Portal;
import rpg.element.bonus.ManaPotion;
import rpg.element.entity.Dragon;
import rpg.element.map.Block;
import rpg.geometry.Vector2D;
import rpg.item.MasterKey;
import rpg.logic.Grid;

public class Level2 extends Game {

	private static final int ROWS = 20;
	private static final int COLUMNS = 20;

	public Level2() {
		super(ROWS, COLUMNS);
		addInitialLocation(new Vector2D(80, 100));
		addInitialLocation(new Vector2D(80, 300));
		addInitialLocation(new Vector2D(280, 300));
		addDynamicElement(new Dragon(new Vector2D(420, 300)));
		addDynamicElement(new ManaPotion(new Vector2D(300, 200)));
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
		addDynamicElement(new ItemHolder(new Vector2D(60, 160), new MasterKey()));
		Portal[] portals = Portal.getPair(grid.getLocation(2, 2), grid.getLocation(10, 6));
		addDynamicElement(portals[0]);
		addDynamicElement(portals[1]);
	}

}
