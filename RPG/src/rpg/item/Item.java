package rpg.item;

import java.util.HashMap;
import java.util.Map;

import rpg.Thing;
import rpg.element.Entity;
import rpg.graphics.draw.Drawer;

public abstract class Item extends Thing {

	private static int counter = 0;
	private static Map<Integer, Item> ids = new HashMap<>();

	public static Item getByID(int id) {
		return ids.get(id);
	}

	public int getID() {
		return getInteger("id");
	}

	public void setID(int id) {
		set("id", id);
		ids.put(id, this);
	}

	public Item() {
		setID(counter++);
	}

	public abstract void onUse(Entity user);

	public abstract Drawer getDrawer();

}
