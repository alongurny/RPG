package rpg;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import physics.Vector;

public class GamePanel extends JPanel {

	private Game game;
	private int sourceX, sourceY;
	private KeyTracker keyTracker;

	public GamePanel(Game game) {
		this.game = game;
		setFocusable(true);
		keyTracker = new KeyTracker();
		addKeyListener(keyTracker);
		keyTracker.addMultiKeyListener(new MultiKeyListener() {

			@Override
			public void keysChange(MultiKeyEvent e) {
				Vector velocity = Vector.ZERO;
				if (e.get(KeyEvent.VK_UP)) {
					velocity = velocity.add(new Vector(0, -1, 0));
				}
				if (e.get(KeyEvent.VK_DOWN)) {
					velocity = velocity.add(new Vector(0, 1, 0));
				}
				if (e.get(KeyEvent.VK_LEFT)) {
					velocity = velocity.add(new Vector(-1, 0, 0));
				}
				if (e.get(KeyEvent.VK_RIGHT)) {
					velocity = velocity.add(new Vector(1, 0, 0));
				}
				game.getPlayers().get(0).setVelocity(velocity);
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				int x = e.getX() - sourceX;
				int y = e.getY() - sourceY;
				Player player = game.getPlayers().get(0);
				Rectangle rect = player.getBoundingRect();
				if (rect.contains(new Point(x, y))) {
					player.removeHealth(10);
				}
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		sourceX = getWidth() / 2;
		sourceY = getHeight() / 2;
		g.translate(sourceX, sourceY);
		for (Element e : game.getElements()) {
			if (e instanceof VisualElement) {
				drawVisualElement(g, (VisualElement) e);
			}
		}
	}

	public void drawVisualElement(Graphics g, VisualElement e) {
		int x = (int) (e.getLocation().getX());
		int y = (int) (e.getLocation().getY());
		g.translate(x, y);
		e.draw(g);
		g.translate(-x, -y);
	}

	public Game getGame() {
		return game;
	}

}
