package rpg.item;

import rpg.element.entity.Entity;
import rpg.graphics.Drawable;

public abstract class Item implements Drawable {

	protected Item() {
	}

	public abstract void onUse(Entity user);

}
