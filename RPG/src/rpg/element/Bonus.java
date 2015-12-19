package rpg.element;

import rpg.element.entity.Entity;
import rpg.level.Level;
import rpg.physics.Vector2D;

public abstract class Bonus extends DynamicElement {

	public Bonus(Vector2D location) {
		super(location);
	}

	@Override
	public void onCollision(Level level, DynamicElement other) {
		if (other instanceof Entity) {
			onPick((Entity) other);
			level.removeElement(this);
		}
	}

	protected abstract void onPick(Entity picker);

	@Override
	public boolean isPassable(Level level, DynamicElement other) {
		return true;
	}
}
