package rpg.item;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
	private List<Item> items;

	public Inventory() {
		items = new ArrayList<>();
	}

	public void add(Item item) {
		items.add(item);
	}

	public boolean remove(Item item) {
		return items.remove(item);
	}

	public boolean contains(Item item) {
		return items.contains(item);
	}

	public int getSize() {
		return items.size();
	}

	public Item get(int index) {
		return items.get(index);
	}

	public Item removeAt(int index) {
		return items.remove(index);
	}
}
