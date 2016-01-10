package rpg.ui;

import javax.swing.JFrame;

import rpg.graphics.draw.MultiAbilityDrawer;

public class GameBoard extends JFrame {

	private static final long serialVersionUID = -3718195330305273764L;
	private GamePanel gamePanel;

	public GameBoard(int width, int height, int num) {
		setResizable(false);
		gamePanel = new GamePanel(num);
		gamePanel.addDrawable(new MultiAbilityDrawer());
		add(gamePanel);
		setSize(width, height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public GamePanel getPanel() {
		return gamePanel;
	}

}
