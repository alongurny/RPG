package rpg.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

/**
 * This class is used by {@link GameClient} to handle IO events: mouse clicks
 * and keyboard events. The logic that needs to be applied when such events
 * occur is already defined here. After being constructed, the only worry of the
 * client needs to be calling the {@link #update() update} method regularly.
 * 
 * @author Alon
 *
 */
public class IOHandler {

	private GameClient client;
	private Set<String> pressed;
	private Map<String, List<Runnable>> typedMap;
	private double direction;

	/**
	 * Constructs a new IO handler for the given client.
	 * 
	 * @param client
	 *            a game client
	 */
	public IOHandler(GameClient client) {
		this.client = client;
		pressed = new ConcurrentSkipListSet<>();
		typedMap = new ConcurrentHashMap<>();
		initialize();
	}

	/**
	 * This method needs to be called regularly for the key bindings to behave
	 * normally.
	 */
	public void update() {
		direction = 0;
		pressed.forEach(s -> Optional.ofNullable(typedMap.get(s)).ifPresent(list -> list.forEach(Runnable::run)));
		client.addCommand("moveHorizontally " + Math.signum(direction));
	}

	private void initialize() {
		bindMouseReleased(e -> {
			Vector2D offset = client.getPanel().getOffset();
			Vector2D target = new Vector2D(e.getX(), e.getY()).subtract(offset);
			if (SwingUtilities.isLeftMouseButton(e)) {
				client.addCommand("leftClick " + target);
			} else if (SwingUtilities.isRightMouseButton(e)) {
				client.addCommand("rightClick " + target);
			}
		});
		bindKeyPressed("Z", () -> client.addCommand("interact"));
		bindKeyPressed("E", () -> client.setShowInventory(true));
		bindKeyReleased("E", () -> client.setShowInventory(false));
		IntStream.rangeClosed(1, 9)
				.forEach(i -> bindKeyPressed(Integer.toString(i), () -> client.addCommand("cast " + i)));
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
			private static final long serialVersionUID = -4714238509876241935L;

			@Override
			public void actionPerformed(ActionEvent e) {
				pressed.add(key);
				runnable.run();
			}
		});
	}

	private void bindKeyReleased(String key, Runnable runnable) {
		InputMap inputMap = client.getPanel().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = client.getPanel().getActionMap();
		inputMap.put(KeyStroke.getKeyStroke("released " + key), "released " + key);
		actionMap.put("released " + key, new AbstractAction() {

			private static final long serialVersionUID = -4762110108621891896L;

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
