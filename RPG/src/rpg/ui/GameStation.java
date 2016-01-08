package rpg.ui;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import rpg.logic.Game;

public abstract class GameStation {

	private GameBoard gameBoard;

	public GameStation(Game game, int num) {
		gameBoard = new GameBoard(480, 528, game, num);
		gameBoard.setLocation(500, 40);
		gameBoard.setAlwaysOnTop(true);
	}

	public GameBoard getBoard() {
		return gameBoard;
	}

	public void addKeyListener(KeyListener listener) {
		gameBoard.setFocusable(true);
		gameBoard.addKeyListener(listener);
	}

	public void start() {
		gameBoard.setVisible(true);
		new Thread(() -> {
			while (true) {
				run();
				gameBoard.repaint();
				try {
					Thread.sleep(30);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public abstract void run();

	public void addMouseListener(MouseListener listener) {
		gameBoard.setFocusable(true);
		gameBoard.getPanel().addMouseListener(listener);
	}

}
