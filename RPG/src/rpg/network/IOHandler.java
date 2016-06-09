package rpg.network;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import rpg.geometry.Vector2D;

public class IOHandler implements MouseListener {

	private GameClient client;
	private Set<String> pressed;
	private Map<String, List<Runnable>> typedMap;
	private double direction;

	public IOHandler(GameClient client) {
		this.client = client;
		pressed = new HashSet<>();
		typedMap = new HashMap<>();
		client.getPanel().addMouseListener(this);
		initialize();
	}

	public void update() {
		direction = 0;
		pressed.forEach(s -> Optional.ofNullable(typedMap.get(s)).ifPresent(list -> list.forEach(Runnable::run)));
		client.addCommand("moveHorizontally " + direction);
	}

	private void initialize() {
		bindPressed("Z", () -> client.addCommand("interact"));
		bindPressed("E", () -> client.setShowInventory(true));
		bindReleased("E", () -> client.setShowInventory(false));
		IntStream.rangeClosed(1, 9)
				.forEach(i -> bindPressed(Integer.toString(i), () -> client.addCommand("cast " + (i - 1))));
		Arrays.asList("UP", "W", "SPACE").forEach(s -> bindTyped(s, () -> client.addCommand("jump")));
		Arrays.asList("DOWN", "S").forEach(s -> bindTyped(s, () -> client.addCommand("fall")));
		Arrays.asList("LEFT", "A").forEach(s -> bindTyped(s, () -> direction += -1));
		Arrays.asList("RIGHT", "D").forEach(s -> bindTyped(s, () -> direction += 1));
	}

	public void bindTyped(String key, Runnable runnable) {
		if (!typedMap.containsKey(key)) {
			typedMap.put(key, new ArrayList<>());
		}
		bindPressed(key, () -> {
		});
		bindReleased(key, () -> {
		});
		typedMap.get(key).add(runnable);
	}

	public void bindPressed(String key, Runnable runnable) {
		InputMap inputMap = client.getPanel().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = client.getPanel().getActionMap();
		inputMap.put(KeyStroke.getKeyStroke(key), key);
		actionMap.put(key, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pressed.add(key);
				runnable.run();
			}
		});
	}

	public void bindReleased(String key, Runnable runnable) {
		InputMap inputMap = client.getPanel().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = client.getPanel().getActionMap();
		inputMap.put(KeyStroke.getKeyStroke("released " + key), "released " + key);
		actionMap.put("released " + key, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pressed.remove(key);
				runnable.run();
			}
		});
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			Vector2D offset = client.getPanel().getOffset();
			Vector2D target = new Vector2D(e.getX(), e.getY()).subtract(offset);
			client.addCommand("onClick " + target);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
