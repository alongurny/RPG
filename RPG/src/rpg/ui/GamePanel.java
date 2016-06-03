package rpg.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = -435064221993994993L;

	private static BufferedImage background;
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
		try {
			background = ImageIO.read(new File("img/background.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.offset = Vector2D.ZERO;
	}

	public void flush() {
		absoluteDrawers.clear();
		absoluteDrawers.addAll(absoluteBuffer);
		absoluteBuffer.clear();
		drawers.clear();
		drawers.addAll(buffer);
		buffer.clear();
	}

	public void addDrawer(Drawer drawer) {
		buffer.add(drawer);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
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

	private void drawDrawer(Graphics2D g, Drawer drawer) {
		int x = (int) (offset.getX());
		int y = (int) (offset.getY());
		g.translate(x, y);
		drawer.draw(g);
		g.translate(-x, -y);
	}

	public void addStaticDrawer(Drawer drawer) {
		statics.add(drawer);
	}

	public Vector2D getOffset() {
		return offset;
	}

	public void setOffset(Vector2D offset) {
		this.offset = offset;
	}

	private static double limit(double value, double min, double max) {
		return Math.min(max, Math.max(min, value));
	}

	public void setOffsetByPlayerLocation(Vector2D location) {
		double x = limit(-location.getX() + getWidth() / 2, -getWidth() / 2 + 32, 32);
		double y = limit(-location.getY() + getHeight() / 2, -getHeight() / 2 + 32, 32);
		setOffset(new Vector2D(x, y));
	}

	public void addAbsoluteDrawer(Drawer drawer) {
		absoluteBuffer.add(drawer);
	}

}
