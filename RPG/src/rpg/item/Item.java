package rpg.item;

import rpg.element.Entity;
import rpg.graphics.draw.Redrawable;

public abstract class Item implements Redrawable {

	public abstract void onUse(Entity user);

}
