package rpg.item;

import rpg.element.Entity;
import rpg.graphics.Sprite;
import rpg.graphics.draw.Drawer;
import rpg.graphics.draw.Redrawer;

public class MasterKey extends Item {

	private Drawer drawer = new Sprite(0, 45, 54);

	public MasterKey() {
	}

	@Override
	public Redrawer getRedrawer() {
		return drawer;
	}

	@Override
	public void onUse(Entity user) {

	}

}
