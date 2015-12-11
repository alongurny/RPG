package rpg.level;

import rpg.Pair;
import rpg.element.Block;
import rpg.element.HealthPotion;
import rpg.element.ManaPotion;
import rpg.element.Player;
import rpg.element.Portal;
import rpg.physics.Vector2D;

public class Level1 extends Level {

	private static final int ROWS = 20;
	private static final int COLUMNS = 20;

	public Level1(Player player) {
		super(ROWS, COLUMNS);
		addElement(player);
		addElement(new HealthPotion(new Vector2D(300, 100)));
		addElement(new ManaPotion(new Vector2D(150, 300)));
		// addElement(new Dragon(new Vector2D(420, 300), new AttributeSet(100,
		// 100, 100)));
		addElement(new ManaPotion(new Vector2D(300, 200)));
		for (int i = 0; i < COLUMNS; i++) {
			getMap().put(new Block(0, i));
			getMap().put(new Block(ROWS - 1, i));
		}
		for (int j = 1; j < ROWS - 1; j++) {
			getMap().put(new Block(j, 0));
			if (j != 12 && j != 13) {
				getMap().put(new Block(j, 4));
			}
			getMap().put(new Block(j, COLUMNS - 1));
		}

		Pair<Portal, Portal> portals = Portal.getPair(4, 2, 9, 5);
		getMap().put(portals.getFirst());
		getMap().put(portals.getSecond());
	}

}
