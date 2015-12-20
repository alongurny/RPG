package rpg.logic;

import rpg.element.Block;
import rpg.element.ManaPotion;
import rpg.element.Portal;
import rpg.element.entity.AttributeSet;
import rpg.element.entity.Dragon;
import rpg.element.entity.Player;
import rpg.physics.Vector2D;

public class Level1 extends Level {

	private static final int ROWS = 20;
	private static final int COLUMNS = 20;

	public Level1(Player player) {
		super(ROWS, COLUMNS);
		addDynamicElement(player);
		addDynamicElement(new Dragon(new Vector2D(420, 300), new AttributeSet()));
		addDynamicElement(new ManaPotion(new Vector2D(300, 200)));
		Grid grid = getMap();
		for (int i = 0; i < COLUMNS; i++) {
			addStaticElement(new Block(grid.getLocation(0, i)));
			addStaticElement(new Block(grid.getLocation(ROWS - 1, i)));
		}
		for (int j = 1; j < ROWS - 1; j++) {
			grid.add(new Block(grid.getLocation(j, 0)));
			if (j != 12 && j != 13) {
				addStaticElement(new Block(grid.getLocation(j, 4)));
			}
			addStaticElement(new Block(grid.getLocation(j, COLUMNS - 1)));
		}
		Portal[] portals = Portal.getPair(grid.getLocation(2, 2), grid.getLocation(10, 6));
		addDynamicElement(portals[0]);
		addDynamicElement(portals[1]);
	}

}
