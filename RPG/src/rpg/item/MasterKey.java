package rpg.item;

import rpg.element.Entity;
import rpg.graphics.Sprite;
import rpg.graphics.draw.Drawer;

public class MasterKey extends Item {

	private Drawer drawer = new Sprite(0, 45, 54);

	public MasterKey() {
	}

	@Override
	public Drawer getDrawer() {
		return drawer;
	}

	@Override
	public void onUse(Entity user) {

	}

}
