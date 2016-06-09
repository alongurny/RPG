package rpg.item;

import rpg.element.entity.Entity;
import rpg.graphics.Drawer;

public abstract class Item {

	public Item() {
	}

	public abstract Drawer getDrawer();

	public abstract void onUse(Entity user);

}
