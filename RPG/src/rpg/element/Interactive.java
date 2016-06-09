package rpg.element;

import rpg.element.entity.Entity;
import rpg.logic.level.Game;

/**
 * This interface is meant to be used by elements. It represents elements that
 * can be interacted with by the player.
 * 
 * @author Alon
 *
 */
public interface Interactive {

	/**
	 * Returns whether this instance can be interacted with. If this method
	 * returns true, {@link #onInteract(Game, Entity) onInteract} will be
	 * called. Otherwise it will not be called.
	 * 
	 * @param game
	 *            the level
	 * @param entity
	 *            the entity that interacts with this instance
	 * @return whether this instance can be interacted with or not.
	 */
	boolean isInteractable(Game game, Entity entity);

	/**
	 * This method will be called when this instance is interacted with.
	 * 
	 * @param game
	 *            the level
	 * @param other
	 *            the entity that interacts with this instance
	 */
	void onInteract(Game game, Entity other);
}
