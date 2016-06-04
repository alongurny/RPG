package rpg.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import javax.swing.JPanel;

import rpg.BufferedImageResource;
import rpg.element.Element;
import rpg.geometry.Vector2D;
import rpg.logic.Grid;
import rpg.logic.level.Game;

public class ServerPanel extends JPanel {

	private static final long serialVersionUID = -435064221993994993L;

	private static BufferedImage bg = BufferedImageResource.get("img/tileset0.png").getImage().getSubimage(32 * 7,
			32 * 15, 32, 32);

	private static int limit(int min, int max, int value) {
		if (min > value) {
			return min;
		}
		if (max < value) {
			return max;
		}
		return value;
	}

	private int sourceX, sourceY;

	private Game game;

	public ServerPanel(Game game) {
		this.game = game;
		setFocusable(true);

	}

	public void drawElement(Graphics g, Element e) {
		int x = (int) (e.getLocation().getX()) - sourceX;
		int y = (int) (e.getLocation().getY()) - sourceY;
		g.translate(x, y);
		e.getDrawer().draw((Graphics2D) g);
		g.translate(-x, -y);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		TexturePaint paint = new TexturePaint(bg, new Rectangle(0, 0, bg.getWidth(), bg.getHeight()));
		Paint prev = g2d.getPaint();
		g2d.setPaint(paint);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setPaint(prev);
		Vector2D loc = new Vector2D(getWidth(), getHeight()).multiply(0.5);
		Grid grid = game.getGrid();
		sourceX = limit(-32, grid.getWidth() - getWidth() + 32, (int) loc.getX() - getWidth() / 2);
		sourceY = limit(-32, grid.getHeight() - getHeight() + 32, (int) loc.getY() - getHeight() / 2);
		List<Element> dynamics = game.getDynamicElements();
		dynamics.sort((a, b) -> a.getIndex() - b.getIndex());
		Queue<Element> dyn = new ArrayDeque<>(dynamics);
		List<Element> statics = game.getStaticElements();
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

	}

}
