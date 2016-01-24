package rpg.ui;

import javax.swing.JFrame;

public class GameBoard extends JFrame {

	private static final long serialVersionUID = -3718195330305273764L;
	private GamePanel gamePanel;

	public GameBoard(int width, int height) {
		setResizable(false);
		gamePanel = new GamePanel();
		add(gamePanel);
		setSize(width, height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public GamePanel getPanel() {
		return gamePanel;
	}

}
