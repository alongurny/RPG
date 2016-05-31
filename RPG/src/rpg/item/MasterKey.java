package rpg.item;

import rpg.element.Entity;
import rpg.graphics.TileDrawer;
import rpg.graphics.draw.Drawer;

public class MasterKey extends Item {

	private Drawer drawer = TileDrawer.tile(0, 45, 54);

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
