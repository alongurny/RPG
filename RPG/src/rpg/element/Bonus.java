package rpg.element;

import rpg.geometry.Vector2D;
import rpg.logic.level.Game;

public abstract class Bonus extends Element {

	public Bonus(Vector2D location) {
		super(location);
	}

	@Override
	public boolean isPassable(Game game, Element other) {
		return true;
	}

	@Override
	public void onCollision(Game game, Element other) {
		if (other instanceof Entity) {
			onPick((Entity) other);
			game.removeDynamicElement(this);
		}
	}

	protected abstract void onPick(Entity picker);
}
