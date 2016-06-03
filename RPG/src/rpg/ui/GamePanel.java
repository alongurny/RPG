package rpg.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

import rpg.BufferedImageResource;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = -435064221993994993L;

	private static BufferedImage bg = BufferedImageResource.get("img/tileset0.png").getImage().getSubimage(32 * 7,
			32 * 15, 32, 32);
	private static double limit(double value, double min, double max) {
		return Math.min(max, Math.max(min, value));
	}
	private List<Drawer> drawers;
	private List<Drawer> buffer;
	private List<Drawer> absoluteDrawers;
	private List<Drawer> absoluteBuffer;
	private List<Drawer> statics;

	private Vector2D offset;

	public GamePanel() {
		this.drawers = new CopyOnWriteArrayList<>();
		this.buffer = new CopyOnWriteArrayList<>();
		this.statics = new CopyOnWriteArrayList<>();
		this.absoluteBuffer = new CopyOnWriteArrayList<>();
		this.absoluteDrawers = new CopyOnWriteArrayList<>();
		setFocusable(true);
		this.offset = Vector2D.ZERO;
	}

	public void addAbsoluteDrawer(Drawer drawer) {
		absoluteBuffer.add(drawer);
	}

	public void addDrawer(Drawer drawer) {
		buffer.add(drawer);
	}

	public void addStaticDrawer(Drawer drawer) {
		statics.add(drawer);
	}

	private void drawDrawer(Graphics2D g, Drawer drawer) {
		int x = (int) (offset.getX());
		int y = (int) (offset.getY());
		g.translate(x, y);
		drawer.draw(g);
		g.translate(-x, -y);
	}

	public void flush() {
		absoluteDrawers.clear();
		absoluteDrawers.addAll(absoluteBuffer);
		absoluteBuffer.clear();
		drawers.clear();
		drawers.addAll(buffer);
		buffer.clear();
	}

	public Vector2D getOffset() {
		return offset;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int y = 0; y < getHeight(); y += 32) {
			for (int x = 0; x < getWidth(); x += 32) {
				g.drawImage(bg, x, y, null);
			}
		}
		for (Drawer drawer : statics) {
			drawDrawer((Graphics2D) g, drawer);
		}
		for (Drawer drawer : drawers) {
			drawDrawer((Graphics2D) g, drawer);
		}
		for (Drawer drawer : absoluteDrawers) {
			drawer.draw((Graphics2D) g);
		}
	}

	public void setOffset(Vector2D offset) {
		this.offset = offset;
	}

	public void setOffsetByPlayerLocation(Vector2D location) {
		double x = limit(-location.getX() + getWidth() / 2, -getWidth() / 2 + 32, 32);
		double y = limit(-location.getY() + getHeight() / 2, -getHeight() / 2 + 32, 32);
		setOffset(new Vector2D(x, y));
	}

}
