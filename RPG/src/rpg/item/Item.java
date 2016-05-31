package rpg.item;

import rpg.Thing;
import rpg.element.Entity;
import rpg.graphics.Drawer;

public abstract class Item extends Thing {

	public Item() {
	}

	public abstract void onUse(Entity user);

	public abstract Drawer getDrawer();

}
