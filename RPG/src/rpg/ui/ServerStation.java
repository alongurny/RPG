package rpg.ui;

import java.awt.event.KeyListener;

import rpg.logic.Game;

public class ServerStation {

	private ServerBoard gameBoard;
	private Game game;

	public ServerStation(Game game) {
		gameBoard = new ServerBoard(480, 480, game);
		gameBoard.setLocation(150, 40);
		gameBoard.setAlwaysOnTop(true);
		this.game = game;
	}

	public void addKeyListener(KeyListener listener) {
		gameBoard.setFocusable(true);
		gameBoard.addKeyListener(listener);
	}

	public void start() {
		gameBoard.setVisible(true);
		new Thread(() -> {
			long last = System.currentTimeMillis();
			while (true) {
				gameBoard.repaint();
				long now = System.currentTimeMillis();
				game.update((now - last) * 1e-3);
				last = now;
			}
		}).start();
	}

}
