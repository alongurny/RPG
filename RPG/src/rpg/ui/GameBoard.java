package rpg.ui;

import javax.swing.JFrame;

import rpg.Game;
import rpg.element.entity.Player;

public class GameBoard extends JFrame {

	private GamePanel gamePanel;

	public GameBoard(int width, int height, Game game, Player player) {
		setResizable(false);
		gamePanel = new GamePanel(game, player);
		add(gamePanel);
		setSize(width, height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}
