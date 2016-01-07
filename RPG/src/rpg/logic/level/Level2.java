package rpg.logic.level;

import rpg.ability.FireballSpell;
import rpg.ability.HasteSpell;
import rpg.element.Block;
import rpg.element.ManaPotion;
import rpg.element.Portal;
import rpg.element.entity.Dragon;
import rpg.element.entity.Player;
import rpg.element.entity.Race;
import rpg.geometry.Vector2D;
import rpg.logic.Grid;

public class Level2 extends Level {

	private static final int ROWS = 20;
	private static final int COLUMNS = 20;

	public Level2() {
		super(ROWS, COLUMNS);

		Player player1 = new Player(new Vector2D(80, 100), Race.HUMAN);
		player1.addAbility(new FireballSpell(192));
		player1.addAbility(new HasteSpell());

		Player player2 = new Player(new Vector2D(80, 300), Race.HUMAN);
		player2.addAbility(new FireballSpell(192));
		player2.addAbility(new HasteSpell());
		addDynamicElement(player1);
		addDynamicElement(player2);
		addDynamicElement(new Dragon(new Vector2D(420, 300)));
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
