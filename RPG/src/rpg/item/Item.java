package rpg.item;

import rpg.Thing;
import rpg.element.entity.Entity;
import rpg.ui.Drawable;

public abstract class Item extends Thing implements Drawable {

	public abstract void onUse(Entity user);

}
