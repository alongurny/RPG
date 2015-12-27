package rpg.ui;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyListener;

import rpg.element.entity.Player;
import rpg.logic.Game;

public abstract class GameStation {

	private MetaBoard metaBoard;
	private GameBoard gameBoard;
	private Game game;

	public GameStation(Game game, Player player) {
		metaBoard = new MetaBoard(480, 200, game, player);
		gameBoard = new GameBoard(480, 480, game, player);
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
		this.game = game;
	}

	public void addKeyListener(KeyListener listener) {
		gameBoard.setFocusable(true);
		gameBoard.addKeyListener(listener);
	}

	public void start() {
		metaBoard.setVisible(true);
		gameBoard.setVisible(true);
		new Thread(() -> {
			long last = System.currentTimeMillis();
			while (true) {
				try {
					Thread.sleep(16);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				doSomething();
				gameBoard.repaint();
				metaBoard.repaint();
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

	public abstract void doSomething();

}
