package rpg;

import rpg.element.Entity;
import rpg.logic.level.Level;

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
	 * returns true, {@link #onInteract(Level, Entity) onInteract} will be
	 * called. Otherwise it will not be called.
	 * 
	 * @param level
	 *            the level
	 * @param entity
	 *            the entity that interacts with this instance
	 * @return whether this instance can be interacted with or not.
	 */
	boolean isInteractable(Level level, Entity entity);

	/**
	 * This method will be called when this instance is interacted with.
	 * 
	 * @param level
	 *            the level
	 * @param other
	 *            the entity that interacts with this instance
	 */
	void onInteract(Level level, Entity other);
}
