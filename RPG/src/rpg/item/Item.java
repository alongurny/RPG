package rpg.item;

import rpg.element.entity.Entity;
import rpg.ui.Drawable;

public abstract class Item implements Drawable {

	public abstract void onUse(Entity user);

}
