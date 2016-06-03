package rpg.element;

import rpg.geometry.Vector2D;
import rpg.logic.level.Level;

public abstract class Bonus extends Element {

	public Bonus(Vector2D location) {
		super(location);
	}

	@Override
	public boolean isPassable(Level level, Element other) {
		return true;
	}

	@Override
	public void onCollision(Level level, Element other) {
		if (other instanceof Entity) {
			onPick((Entity) other);
			level.removeDynamicElement(this);
		}
	}

	protected abstract void onPick(Entity picker);
}
