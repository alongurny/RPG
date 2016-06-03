package rpg.logic.level;

import rpg.element.Block;
import rpg.element.Door;
import rpg.element.Dragon;
import rpg.element.ItemHolder;
import rpg.element.ManaPotion;
import rpg.element.Player;
import rpg.element.Portal;
import rpg.element.entity.Profession;
import rpg.element.entity.Race;
import rpg.geometry.Vector2D;
import rpg.item.MasterKey;
import rpg.logic.Grid;

public class Level2 extends Level {

	private static final int ROWS = 20;
	private static final int COLUMNS = 20;

	public Level2() {
		super(ROWS, COLUMNS);
		Player player1 = new Player(new Vector2D(80, 100), Race.HUMAN, Profession.FIRE_MAGE);
		Player player2 = new Player(new Vector2D(80, 300), Race.HUMAN, Profession.FROST_MAGE);
		addInitialLocation(player1.getLocation());
		addInitialLocation(player2.getLocation());
		addPlayer(player1);
		addPlayer(player2);
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
