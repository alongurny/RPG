package rpg.ui;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;

import rpg.Messages;
import rpg.network.GameClient;
import rpg.network.IOHandler;

public class GameStation {

	private JFrame frame;
	private GamePanel panel;
	private GameClient client;

	public GameStation(Socket socket) throws IOException {
		frame = new JFrame(Messages.getString("GameStation.title"));
		frame.setLocation(700, 40);
		frame.setAlwaysOnTop(true);
		panel = new GamePanel();
		frame.setSize(480, 540);
		frame.add(panel);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client = new GameClient(panel, socket);
		IOHandler handler = new IOHandler(client);
		addKeyListener(handler);
		addMouseListener(handler);
	}

	public void addKeyListener(KeyListener listener) {
		frame.setFocusable(true);
		frame.addKeyListener(listener);
	}

	public void addMouseListener(MouseListener listener) {
		frame.setFocusable(true);
		panel.addMouseListener(listener);
	}

	public void start() {
		frame.setVisible(true);
		new Thread(() -> {
			while (true) {
				client.sendCommands();
				frame.repaint();
				try {
					Thread.sleep(15);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
