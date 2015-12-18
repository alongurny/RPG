package rpg;

import rpg.element.entity.Entity;
import rpg.level.Level;

public interface Interactive {
	void onInteract(Level level, Entity other);

	boolean isInteractable(Level level, Entity other);
}
