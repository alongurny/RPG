package rpg.network;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import rpg.geometry.Vector2D;

public class IOHandler {

	private GameClient client;
	private Set<String> pressed;
	private Map<String, List<Runnable>> typedMap;
	private double direction;

	public IOHandler(GameClient client) {
		this.client = client;
		pressed = new ConcurrentSkipListSet<>();
		typedMap = new ConcurrentHashMap<>();
		initialize();
	}

	public void update() {
		direction = 0;
		pressed.forEach(s -> Optional.ofNullable(typedMap.get(s)).ifPresent(list -> list.forEach(Runnable::run)));
		client.addCommand("moveHorizontally " + direction);
	}

	private void initialize() {
		bindMouseReleased(e -> {
			if (SwingUtilities.isRightMouseButton(e)) {
				Vector2D offset = client.getPanel().getOffset();
				Vector2D target = new Vector2D(e.getX(), e.getY()).subtract(offset);
				client.addCommand("onClick " + target);
			}
		});
		bindKeyPressed("Z", () -> client.addCommand("interact"));
		bindKeyPressed("E", () -> client.setShowInventory(true));
		bindKeyReleased("E", () -> client.setShowInventory(false));
		IntStream.rangeClosed(1, 9)
				.forEach(i -> bindKeyPressed(Integer.toString(i), () -> client.addCommand("cast " + (i - 1))));
		Arrays.asList("UP", "W", "SPACE").forEach(s -> bindKeyTyped(s, () -> client.addCommand("jump")));
		Arrays.asList("DOWN", "S").forEach(s -> bindKeyTyped(s, () -> client.addCommand("fall")));
		Arrays.asList("LEFT", "A").forEach(s -> bindKeyTyped(s, () -> direction += -1));
		Arrays.asList("RIGHT", "D").forEach(s -> bindKeyTyped(s, () -> direction += 1));
	}

	private void bindKeyTyped(String key, Runnable runnable) {
		if (!typedMap.containsKey(key)) {
			typedMap.put(key, new CopyOnWriteArrayList<>());
		}
		bindKeyPressed(key, () -> {
		});
		bindKeyReleased(key, () -> {
		});
		typedMap.get(key).add(runnable);
	}

	private void bindKeyPressed(String key, Runnable runnable) {
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

	public void bindKeyReleased(String key, Runnable runnable) {
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

	private void bindMouseReleased(Consumer<MouseEvent> action) {
		client.getPanel().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				action.accept(e);
			}
		});
	}
}
