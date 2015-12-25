package rpg.ui;

import javax.swing.JFrame;

import rpg.element.entity.Player;
import rpg.logic.Game;

public class GameBoard extends JFrame {

	private static final long serialVersionUID = -3718195330305273764L;
	private GamePanel gamePanel;

	public GameBoard(int width, int height, Game game, Player player) {
		setResizable(false);
		gamePanel = new GamePanel(game, player);
		add(gamePanel);
		setSize(width, height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public GamePanel getPanel() {
		return gamePanel;
	}

}
