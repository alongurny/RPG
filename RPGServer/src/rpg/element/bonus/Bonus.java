package rpg.element.bonus;

import rpg.element.Element;
import rpg.element.Interactive;
import rpg.element.entity.Entity;
import rpg.geometry.Vector2D;
import rpg.logic.level.Game;

public abstract class Bonus extends Element implements Interactive {

	public Bonus(Vector2D location) {
		super(location);
	}

	@Override
	public boolean isPassable(Game game, Element other) {
		return true;
	}

	@Override
	public void onCollision(Game game, Element other) {
	}

	@Override
	public boolean isInteractable(Game game, Entity entity) {
		return entity.getAbsoluteRect().intersects(this.getAbsoluteRect());
	}

	@Override
	public void onInteract(Game game, Entity other) {
		onPick(other);
		game.removeDynamicElement(this);
	}

	protected abstract void onPick(Entity picker);
}
