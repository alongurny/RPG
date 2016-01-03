package rpg.ui;

import java.awt.event.KeyListener;

import rpg.logic.Game;

public abstract class ServerStation {

	private ServerBoard gameBoard;
	private Game game;

	public ServerStation(Game game) {
		gameBoard = new ServerBoard(480, 480, game);
		gameBoard.setLocation(500, 40);
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
				doSomething();
				gameBoard.repaint();
				try {
					Thread.sleep(10);
				} catch (Exception e) {
					e.printStackTrace();
				}
				long now = System.currentTimeMillis();
				long diff = now - last;
				game.update(diff * 1e-3);
				last = now;

			}
		}).start();
	}

	public abstract void doSomething();
}
