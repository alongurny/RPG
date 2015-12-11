package rpg.element;

import rpg.level.Level;
import rpg.physics.Vector2D;

public abstract class Bonus extends DynamicElement {

	public Bonus(Vector2D location) {
		super(location);
	}

	@Override
	public void onCollision(Level level, Element other) {
		if (other instanceof Entity) {
			onPick((Entity) other);
			level.removeElement(this);
		}
	}

	protected abstract void onPick(Entity picker);

	@Override
	public boolean isPassable(Level level, Element other) {
		return true;
	}
}
