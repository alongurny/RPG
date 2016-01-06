package rpg.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import rpg.element.Element;
import rpg.element.entity.Player;
import rpg.geometry.Vector2D;
import rpg.logic.Game;
import rpg.logic.Grid;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = -435064221993994993L;

	private static BufferedImage background;
	private int sourceX, sourceY;
	private Game game;
	private int num;
	private List<Drawable> drawables;

	public GamePanel(Game game, int num) {
		this.drawables = new ArrayList<>();
		this.game = game;
		this.num = num;
		setFocusable(true);
		try {
			background = ImageIO.read(new File("img/background.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void addDrawable(Drawable drawable) {
		drawables.add(drawable);
	}

	public Player getPlayer() {
		return game.getLevel().getPlayer(num);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Vector2D loc = getPlayer().getLocation();
		Grid grid = game.getLevel().getMap();
		sourceX = limit(-32, grid.getWidth() - getWidth() + 32, (int) loc.getX() - getWidth() / 2);
		sourceY = limit(-32, grid.getHeight() - getHeight() + 32, (int) loc.getY() - getHeight() / 2);
		g.drawImage(background, 0, 0, null);
		List<Element> dynamics = game.getLevel().getDynamicElements();
		dynamics.sort((a, b) -> a.getIndex() - b.getIndex());
		Queue<Element> dyn = new ArrayDeque<>(dynamics);
		List<Element> statics = game.getLevel().getStaticElements();
		statics.sort((a, b) -> a.getIndex() - b.getIndex());
		Queue<Element> stt = new ArrayDeque<>(statics);
		while (!dyn.isEmpty() && !stt.isEmpty()) {
			Element s = stt.peek();
			Element d = dyn.peek();
			if (s.getIndex() < d.getIndex()) {
				drawElement(g, stt.remove());
			} else {
				drawElement(g, dyn.remove());
			}
		}
		while (!stt.isEmpty()) {
			drawElement(g, stt.remove());
		}
		while (!dyn.isEmpty()) {
			drawElement(g, dyn.remove());
		}
		g.translate(0, getHeight() - 48);
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, getWidth(), 48);
		g.translate(10, 10);
		for (Drawable d : drawables) {
			d.draw(g);
			g.translate(0, 64);
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

	public void drawElement(Graphics g, Element e) {
		int x = (int) (e.getLocation().getX()) - sourceX;
		int y = (int) (e.getLocation().getY()) - sourceY;
		g.translate(x, y);
		e.draw(g);
		g.translate(-x, -y);
	}

}
