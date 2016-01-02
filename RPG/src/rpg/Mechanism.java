package rpg;

import rpg.logic.level.Level;

public abstract class Mechanism extends Thing {

	public abstract void update(Level level, double dt);

}
