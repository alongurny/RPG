package rpg.item;

import java.util.HashMap;
import java.util.Map;

import rpg.Thing;
import rpg.element.Entity;
import rpg.exception.RPGException;
import rpg.graphics.draw.Drawer;

public abstract class Item extends Thing {

	private static int counter = 0;
	private static Map<Integer, Item> ids = new HashMap<>();

	public static Item getByID(int id) {
		Item item = ids.get(id);
		if (item == null) {
			throw new RPGException("No item with id " + id);
		}
		return item;
	}

	public int getID() {
		return getInteger("id");
	}

	public void setID(int id) {
		ids.remove(getInteger("id", -1));
		ids.put(id, this);
		set("id", id);
	}

	public Item() {
		setID(counter++);
		System.out.printf("%s, %d\n", this, getID());
	}

	public abstract void onUse(Entity user);

	public abstract Drawer getDrawer();

}
