package rpg.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import rpg.geometry.Vector2D;
import rpg.graphics.draw.Drawer;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = -435064221993994993L;

	private static BufferedImage background;
	private List<Drawer> drawers;
	private List<Drawer> buffer;
	private List<Drawer> statics;
	private Vector2D offset;

	public GamePanel() {
		this.drawers = new CopyOnWriteArrayList<>();
		this.buffer = new CopyOnWriteArrayList<>();
		this.statics = new CopyOnWriteArrayList<>();
		setFocusable(true);
		try {
			background = ImageIO.read(new File("img/background.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.offset = Vector2D.ZERO;
	}

	public void flush() {
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
			drawDrawer(g, drawer);
		}
		for (Drawer drawer : drawers) {
			drawDrawer(g, drawer);
		}
	}

	private void drawDrawer(Graphics g, Drawer drawer) {
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

}
