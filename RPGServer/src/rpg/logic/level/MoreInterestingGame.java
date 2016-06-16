package rpg.logic.level;

public class MoreInterestingGame extends Game {

	public MoreInterestingGame() {
		super(16, 100);
		GameLoader.load(this, "game.map");
	}

}
