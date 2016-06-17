package rpg.server;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

import rpg.logic.level.Game;

/**
 * This class is used for UI for the server.
 * 
 * @author Alon
 *
 */
public class ServerStation {

	private boolean started;
	private JFrame frame;

	/**
	 * Initializes a new ServerStation
	 * 
	 * @param game
	 *            the game
	 * @throws IOException
	 *             if an I/O exception occurs
	 */
	public ServerStation(Game game) throws IOException {
		GameServer gameServer = new GameServer(game);
		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!started) {
					gameServer.start();
					startButton.setText("Exit");
				} else {
					System.exit(0);
				}
				started = !started;
			}
		});
		frame = new JFrame("Last Bender - server");
		frame.setSize(300, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		frame.add(startButton);
		frame.setResizable(false);
	}

	public void start() {
		frame.setVisible(true);
	}

}
