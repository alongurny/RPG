package rpg.ui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import rpg.AbilityHandler;
import rpg.Game;
import rpg.Map;
import rpg.element.DynamicElement;
import rpg.element.Player;
import rpg.element.StaticElement;
import rpg.physics.Vector2D;

public class GamePanel extends JPanel {

	private Player player;
	private KeyTracker keyTracker;
	private static BufferedImage background;

	private int sourceX, sourceY;
	private Game game;

	public GamePanel(Game game, Player player) {
		this.player = player;
		this.game = game;
		setFocusable(true);
		keyTracker = new KeyTracker();
		addKeyListener(keyTracker);
		try {
			background = ImageIO.read(new File("img/background.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		keyTracker.addMultiKeyListener(new MultiKeyListener() {

			@Override
			public void keysChange(MultiKeyEvent e) {
				Vector2D velocity = Vector2D.ZERO;

				if (e.get(KeyEvent.VK_UP)) {
					velocity = velocity.add(Vector2D.NORTH);
				}
				if (e.get(KeyEvent.VK_DOWN)) {
					velocity = velocity.add(Vector2D.SOUTH);
				}
				if (e.get(KeyEvent.VK_LEFT)) {
					velocity = velocity.add(Vector2D.WEST);
				}
				if (e.get(KeyEvent.VK_RIGHT)) {
					velocity = velocity.add(Vector2D.EAST);
				}

				player.setVelocity(velocity.multiply(3));
				if (!velocity.equals(Vector2D.ZERO)) {
					player.setDirection(velocity);
				}
			}
		});
		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					game.getLevel().tryInteract(player);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_1) {
					AbilityHandler ah = player.getAbilityHandler();
					ah.tryCast(game.getLevel(), ah.getAbility(0));
				}

			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int px = (int) player.getLocation().getX();
				int py = (int) player.getLocation().getY();
				int x = e.getX() - px;
				int y = e.getY() - py;
				Rectangle rect = player.getRelativeRect();
				if (rect.contains(new Point(x, y))) {
					player.removeBarValue(10, "health");
				}
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Vector2D loc = player.getLocation();
		Map map = game.getLevel().getMap();
		sourceX = limit(-32, map.getWidth() - getWidth() + 32, (int) loc.getX() - getWidth() / 2);
		sourceY = limit(-32, map.getHeight() - getHeight() + 32, (int) loc.getY() - getHeight() / 2);
		g.drawImage(background, 0, 0, null);
		List<DynamicElement> dynamics = game.getLevel().getDynamicElements();
		dynamics.sort((a, b) -> a.getIndex() - b.getIndex());
		Queue<DynamicElement> dyn = new ArrayDeque<>(dynamics);
		List<StaticElement> statics = game.getLevel().getMap().getElements();
		statics.sort((a, b) -> a.getIndex() - b.getIndex());
		Queue<StaticElement> stt = new ArrayDeque<>(statics);
		while (!dyn.isEmpty() && !stt.isEmpty()) {
			StaticElement s = stt.peek();
			DynamicElement d = dyn.peek();
			if (s.getIndex() < d.getIndex()) {
				drawStaticElement(g, stt.remove());
			} else {
				drawDynamicElement(g, dyn.remove());
			}
		}
		while (!stt.isEmpty()) {
			drawStaticElement(g, stt.remove());
		}
		while (!dyn.isEmpty()) {
			drawDynamicElement(g, dyn.remove());
		}

	}

	private static int limit(int min, int max, int value) {
		if (min > value) {
			return min;
		}
		if (max < value) {
			return max;
		}
		return value;
	}

	public void drawStaticElement(Graphics g, StaticElement e) {
		int x = e.getX() * game.getLevel().getMap().getColumnWidth() - sourceX;
		int y = e.getY() * game.getLevel().getMap().getRowHeight() - sourceY;
		g.translate(x, y);
		e.draw(g);
		g.translate(-x, -y);
	}

	public void drawDynamicElement(Graphics g, DynamicElement e) {
		int x = (int) (e.getLocation().getX()) - sourceX;
		int y = (int) (e.getLocation().getY()) - sourceY;
		g.translate(x, y);
		e.draw(g);
		g.translate(-x, -y);
	}

}
