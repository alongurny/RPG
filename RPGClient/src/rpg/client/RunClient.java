package rpg.client;

import java.net.Socket;

import javax.swing.SwingUtilities;

public class RunClient {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				new GameStation(new Socket("localhost", 1234));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
