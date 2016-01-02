package rpg.ui;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyListener;

import rpg.logic.Game;

public abstract class GameStation {

	private MetaBoard metaBoard;
	private GameBoard gameBoard;

	public GameStation(Game game, int num) {
		metaBoard = new MetaBoard(480, 200, game, num);
		gameBoard = new GameBoard(480, 480, game, num);
		gameBoard.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent e) {
				metaBoard.setLocation(gameBoard.getLocation().x,
						gameBoard.getLocation().y + (int) gameBoard.getSize().getHeight());
			}
		});
		gameBoard.setLocation(500, 40);
		gameBoard.setAlwaysOnTop(true);
		metaBoard.setAlwaysOnTop(true);
	}

	public void addKeyListener(KeyListener listener) {
		gameBoard.setFocusable(true);
		gameBoard.addKeyListener(listener);
	}

	public void start() {
		metaBoard.setVisible(true);
		gameBoard.setVisible(true);
		new Thread(() -> {
			while (true) {
				doSomething();
				gameBoard.repaint();
				metaBoard.repaint();
			}
		}).start();
	}

	public abstract void doSomething();

}
