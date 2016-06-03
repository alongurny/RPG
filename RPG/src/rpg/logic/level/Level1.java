package rpg.logic.level;

import rpg.ability.FireballSpell;
import rpg.ability.HasteSpell;
import rpg.element.Block;
import rpg.element.Player;
import rpg.element.entity.Profession;
import rpg.element.entity.Race;
import rpg.geometry.Vector2D;
import rpg.logic.Grid;

public class Level1 extends Level {

	private static final int ROWS = 20;
	private static final int COLUMNS = 20;

	public Level1() {
		super(ROWS, COLUMNS);
		Player player = new Player(new Vector2D(80, 100), Race.HUMAN, Profession.FROST_MAGE);
		player.addAbility(new FireballSpell());
		player.addAbility(new HasteSpell());
		addPlayer(player);
		Grid grid = getGrid();
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

	}

}
