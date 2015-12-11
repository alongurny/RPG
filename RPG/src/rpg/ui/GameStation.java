package rpg.ui;

import rpg.AttributeSet;
import rpg.FireballSpell;
import rpg.Game;
import rpg.Race;
import rpg.element.Player;
import rpg.level.Level;
import rpg.level.Level1;
import rpg.physics.Vector2D;

public class GameStation {

	private MetaBoard metaBoard;
	private GameBoard gameBoard;
	private Game game;

	public GameStation(Game game, Player player) {
		metaBoard = new MetaBoard(480, 480, game, player);
		metaBoard.setLocation(100, 100);
		gameBoard = new GameBoard(480, 480, game, player);
		gameBoard.setLocation(600, 100);
		this.game = game;
	}

	public void start() {
		metaBoard.setVisible(true);
		gameBoard.setVisible(true);
		new Thread(() -> {
			while (true) {
				game.update();
				gameBoard.repaint();
				metaBoard.repaint();
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	public static void main(String[] args) {
		Player player = new Player(new Vector2D(100, 100), new AttributeSet(13, 13, 13), Race.HUMAN);
		player.getAbilityHandler().addAbility(new FireballSpell(player, 7));
		Level level = new Level1(player);
		Game game = new Game(level);
		GameStation gs = new GameStation(game, player);
		level.setNextLevel(new Level1(player));
		gs.start();

	}

}
