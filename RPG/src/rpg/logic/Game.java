package rpg.logic;

public class Game {
	private Level level;

	public Game(Level firstLevel) {
		this.level = firstLevel;
	}

	public void update(double d) {
		if (level != null) {
			if (level.isFinished() && level.getNextLevel() != null) {
				level = level.getNextLevel();
			} else {
				level.update(d);
			}
		}
	}

	public Level getLevel() {
		return level;
	}
}
