package rpg.logic;

import rpg.logic.level.Level;

public class Game {
	private Level level;

	public Game(Level firstLevel) {
		this.level = firstLevel;
	}

	public void update(double dt) {
		if (level != null) {
			if (level.isFinished() && level.getNextLevel() != null) {
				level = level.getNextLevel();
			} else {
				level.update(dt);
			}
		}
	}

	public Level getLevel() {
		return level;
	}
}
