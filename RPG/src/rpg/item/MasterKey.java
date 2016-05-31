package rpg.item;

import rpg.element.Entity;
import rpg.graphics.Drawer;
import rpg.graphics.TileDrawer;

public class MasterKey extends Item {

	private Drawer drawer = new TileDrawer(0, 45, 54);

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
