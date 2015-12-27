package rpg.ui;

import java.awt.event.KeyListener;

import rpg.logic.Game;

public class ServerStation {

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
				try {
					Thread.sleep(16);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				gameBoard.repaint();
				long now = System.currentTimeMillis();
				game.update(20e-3);
				long diff = (now - last);
				if (diff < 20) {
					try {
						Thread.sleep(19 - diff);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				last = now;

			}
		}).start();
	}

}
