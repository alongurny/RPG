package rpg.client;

import java.awt.FlowLayout;
import java.net.Socket;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import external.Messages;

/**
 * The game station has two roles: the first is to provide sufficient GUI. It
 * provides a frame that the client can use to choose their profession, and then
 * the game runs in another {@link JFrame} that contains the {@link GamePanel}.
 * The second is to send commands to the server regularly.
 * 
 * @author Alon
 *
 */
public class GameStation {

	private Map<String, String> professionsMap = new HashMap<>();

	private void load(String simpleName, String name) {
		professionsMap.put(simpleName, "rpg.element.entity.profession." + name);
	}

	private JFrame frame;
	private JFrame optionFrame;
	private GamePanel panel;
	private GameClient client;
	private IOHandler handler;

	/**
	 * Constructs a new <code>GameStation</code> with the given socket.
	 * 
	 * @param socket
	 *            a socket to connect to the server
	 */
	public GameStation(Socket socket) {
		load("Air Mage", "AirMage");
		load("Fire Mage", "FireMage");
		load("Force Mage", "ForceMage");
		load("Frost Mage", "FrostMage");
		load("Warrior", "Warrior");
		panel = new GamePanel();
		optionFrame = new JFrame("Last Bender");
		optionFrame.setSize(600, 400);
		optionFrame.setLayout(new FlowLayout());
		optionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		optionFrame.add(new JLabel("Choose your profession: "));
		ButtonGroup professionsGroup = new ButtonGroup();
		for (String profession : professionsMap.keySet()) {
			JRadioButton b = new JRadioButton(profession);
			professionsGroup.add(b);
			optionFrame.add(b);
		}
		JButton button = new JButton("Play!");
		optionFrame.add(button);
		frame = new JFrame(Messages.getString("GameStation.title"));
		frame.setLocation(700, 40);
		frame.setAlwaysOnTop(true);
		frame.setFocusable(true);
		frame.setSize(480, 540);
		frame.add(panel);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		button.addActionListener(e -> {
			try {
				Optional<String> profession = Optional.empty();
				Enumeration<AbstractButton> pe = professionsGroup.getElements();
				while (pe.hasMoreElements()) {
					AbstractButton b = pe.nextElement();
					if (b.isSelected()) {
						profession = Optional.of(professionsMap.get(b.getText()));
						break;
					}
				}
				if (profession.isPresent()) {
					client = new GameClient(panel, socket, profession.get());
					handler = new IOHandler(client);
					frame.setVisible(true);
					optionFrame.setVisible(false);
					new Thread(() -> {
						while (true) {
							client.sendCommands();
							handler.update();
							frame.repaint();
							try {
								Thread.sleep(15);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}).start();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		optionFrame.setVisible(true);
	}

}
