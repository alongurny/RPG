package rpg;

import rpg.logic.level.Game;
import rpg.network.GameServer;

/**
 * Represents something that can be updated regularly.
 * {@link #update(Game, double) update} will be called repeatedly by the
 * {@link GameServer}.
 * 
 * @author Alon
 *
 */
public interface Updatable {

	/**
	 * This method will be called to update this instance.
	 * 
	 * @param game
	 *            the level
	 * @param dt
	 *            the time passed in seconds
	 */
	void update(Game game, double dt);

}
