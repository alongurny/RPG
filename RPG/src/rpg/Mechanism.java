package rpg;

import rpg.logic.level.Level;
import rpg.ui.ServerStation;

/**
 * Represents something that can be updated regularly.
 * {@link #update(Level, double) update} will be called repeatedly by the
 * {@link ServerStation}.
 * 
 * @author Alon
 *
 */
public interface Mechanism {

	/**
	 * This method will be called to update this instance.
	 * 
	 * @param level
	 *            the level
	 * @param dt
	 *            the time passed in seconds
	 */
	void update(Level level, double dt);

}
