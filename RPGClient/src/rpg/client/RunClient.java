package rpg.client;

import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import external.Messages;

public class RunClient {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				String ip = JOptionPane.showInputDialog("Insert IP address of the server");
				new GameStation(
						new Socket(ip != null && ip.length() > 0 ? ip : "localhost", Messages.getInt("TCP.port")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
