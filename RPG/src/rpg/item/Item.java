package rpg.item;

import rpg.element.entity.Entity;
import rpg.graphics.Drawable;

/**
 * Item is something that can be contained in an {@link Entity}'s inventory and
 * used.
 * 
 * @author Alon
 *
 */
public abstract class Item implements Drawable {

	protected Item() {
	}

	/**
	 * Called when an entity uses this item.
	 * 
	 * @param user
	 *            the entity who uses this item.
	 */
	public abstract void onUse(Entity user);

}
