package rpg.ui;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.Socket;

import rpg.network.GameClient;
import rpg.network.IOHandler;

public class GameStation {

	private GameBoard gameBoard;
	private GameClient client;

	public GameStation(Socket socket) throws IOException {
		gameBoard = new GameBoard(480, 528);
		gameBoard.setLocation(700, 40);
		gameBoard.setAlwaysOnTop(true);
		client = new GameClient(gameBoard.getPanel(), socket);
		IOHandler handler = new IOHandler(client);
		addKeyListener(handler);
		addMouseListener(handler);
	}

	public void addKeyListener(KeyListener listener) {
		gameBoard.setFocusable(true);
		gameBoard.addKeyListener(listener);
	}

	public void start() {
		gameBoard.setVisible(true);
		new Thread(() -> {
			while (true) {
				client.sendCommands();
				gameBoard.repaint();
				try {
					Thread.sleep(30);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void addMouseListener(MouseListener listener) {
		gameBoard.setFocusable(true);
		gameBoard.getPanel().addMouseListener(listener);
	}

}
