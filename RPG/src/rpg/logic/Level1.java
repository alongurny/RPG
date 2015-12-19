package rpg.logic;

import rpg.element.Block;
import rpg.element.Dragon;
import rpg.element.ManaPotion;
import rpg.element.Portal;
import rpg.element.entity.AttributeSet;
import rpg.element.entity.Player;
import rpg.physics.Vector2D;

public class Level1 extends Level {

	private static final int ROWS = 20;
	private static final int COLUMNS = 20;

	public Level1(Player player) {
		super(ROWS, COLUMNS);
		addElement(player);
		addElement(new Dragon(new Vector2D(420, 300), new AttributeSet(100, 100, 100)));
		addElement(new ManaPotion(new Vector2D(300, 200)));
		Map map = getMap();
		for (int i = 0; i < COLUMNS; i++) {
			map.put(new Block(map.getLocation(0, i)));
			map.put(new Block(map.getLocation(ROWS - 1, i)));
		}
		for (int j = 1; j < ROWS - 1; j++) {
			map.put(new Block(map.getLocation(j, 0)));
			if (j != 12 && j != 13) {
				getMap().put(new Block(map.getLocation(j, 4)));
			}
			map.put(new Block(map.getLocation(j, COLUMNS - 1)));
		}

		Portal[] portals = Portal.getPair(new Vector2D(250, 130), new Vector2D(600, 400));
		getMap().put(portals[0]);
		getMap().put(portals[1]);
	}

}
