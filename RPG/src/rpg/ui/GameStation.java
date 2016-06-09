package rpg.ui;

import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;

import external.Messages;
import rpg.network.GameClient;
import rpg.network.IOHandler;

public class GameStation {

	private JFrame frame;
	private GamePanel panel;
	private GameClient client;
	private IOHandler handler;

	public GameStation(Socket socket) throws IOException {
		frame = new JFrame(Messages.getString("GameStation.title"));
		frame.setLocation(700, 40);
		frame.setAlwaysOnTop(true);
		frame.setFocusable(true);
		panel = new GamePanel();
		frame.setSize(480, 540);
		frame.add(panel);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client = new GameClient(panel, socket);
		handler = new IOHandler(client);
	}

	public void start() {
		frame.setVisible(true);
		new Thread(() -> {
			while (true) {
				client.sendCommands();
				handler.update();
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
