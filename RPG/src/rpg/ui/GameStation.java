package rpg.ui;

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
import rpg.element.entity.profession.FireMage;
import rpg.element.entity.profession.ForceMage;
import rpg.element.entity.profession.FrostMage;
import rpg.element.entity.profession.StoneMage;
import rpg.element.entity.race.Human;
import rpg.network.GameClient;
import rpg.network.IOHandler;

public class GameStation {

	private Map<String, String> racesMap = new HashMap<>();
	private Map<String, String> professionsMap = new HashMap<>();
	
	private void load(Class<?> cls, Map<String, String> map) {
		map.put(cls.getSimpleName(), cls.getName());
	}

	private JFrame frame;
	private JFrame optionFrame;
	private GamePanel panel;
	private GameClient client;
	private IOHandler handler;

	public GameStation(Socket socket) {
		load(Human.class, racesMap);
		load(FireMage.class, professionsMap);
		load(FrostMage.class, professionsMap);
		load(ForceMage.class, professionsMap);
		load(StoneMage.class, professionsMap);
		panel = new GamePanel();
		optionFrame = new JFrame("Last Bender");
		optionFrame.setSize(600, 400);
		optionFrame.setLayout(new FlowLayout());
		optionFrame.add(new JLabel("Choose your race: "));
		ButtonGroup racesGroup = new ButtonGroup();
		for (String race : racesMap.keySet()) {
			JRadioButton b = new JRadioButton(race);
			racesGroup.add(b);
			optionFrame.add(b);
		}
		optionFrame.add(new JLabel("Choose your profession: "));
		ButtonGroup professionsGroup = new ButtonGroup();
		for (String profession : professionsMap.keySet()) {
			JRadioButton b = new JRadioButton(profession);
			professionsGroup.add(b);
			optionFrame.add(b);
		}
		JButton button = new JButton("Send");
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
				Optional<String> race = Optional.empty();
				Enumeration<AbstractButton> re = racesGroup.getElements();
				while (re.hasMoreElements()) {
					AbstractButton b = re.nextElement();
					if (b.isSelected()) {
						race = Optional.of(racesMap.get(b.getText()));
						break;
					}
				}
				Optional<String> profession = Optional.empty();
				Enumeration<AbstractButton> pe = professionsGroup.getElements();
				while (pe.hasMoreElements()) {
					AbstractButton b = pe.nextElement();
					if (b.isSelected()) {
						System.out.println(b.getText());
						profession = Optional.of(professionsMap.get(b.getText()));
						break;
					}
				}
				if (race.isPresent() && profession.isPresent()) {
					optionFrame.setVisible(false);
					client = new GameClient(panel, socket, race.get(), profession.get());
					handler = new IOHandler(client);
					frame.setVisible(true);
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
