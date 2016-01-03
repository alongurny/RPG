package rpg.ui;

import javax.swing.JFrame;

import rpg.logic.Game;

public class ServerBoard extends JFrame {

	private static final long serialVersionUID = -3718195330305273764L;
	private ServerPanel gamePanel;

	public ServerBoard(int width, int height, Game game) {
		setResizable(false);
		gamePanel = new ServerPanel(game);
		add(gamePanel);
		setSize(width, height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

}
