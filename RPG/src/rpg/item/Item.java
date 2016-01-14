package rpg.item;

import rpg.Thing;
import rpg.element.Entity;
import rpg.graphics.draw.Drawable;

public abstract class Item extends Thing implements Drawable {

	public abstract void onUse(Entity user);

}
