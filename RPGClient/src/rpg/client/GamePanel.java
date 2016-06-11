package rpg.client;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

import external.Messages;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.Tileset;

/**
 * The <code>GamePanel</code> is the panel that draws the game in the client
 * size. It has no actual information on the game, except for the location of
 * the player. It only knows what to draw.
 * 
 * @author Alon
 *
 */
public class GamePanel extends JPanel {

	private static final long serialVersionUID = -435064221993994993L;

	private static BufferedImage bg = Tileset.get(Messages.getInt("GamePanel.bg.tileset"))
			.getTile(Messages.getInt("GamePanel.bg.row"), Messages.getInt("GamePanel.bg.column"));

	private static double limit(double value, double min, double max) {
		return Math.min(max, Math.max(min, value));
	}

	private List<Drawer> drawers;
	private List<Drawer> buffer;
	private List<Drawer> absoluteDrawers;
	private List<Drawer> absoluteBuffer;
	private List<Drawer> statics;
	private Vector2D offset;
	private Vector2D dimensions;

	/**
	 * Constructs a new GamePanel
	 */
	public GamePanel() {
		this.drawers = new CopyOnWriteArrayList<>();
		this.buffer = new CopyOnWriteArrayList<>();
		this.statics = new CopyOnWriteArrayList<>();
		this.absoluteBuffer = new CopyOnWriteArrayList<>();
		this.absoluteDrawers = new CopyOnWriteArrayList<>();
		setFocusable(true);
		this.offset = Vector2D.ZERO;
		this.dimensions = new Vector2D(getWidth(), getHeight());
	}

	/**
	 * Add a new absolute drawer. Absolute drawers' initial position of drawing
	 * is independent of the {@link #getOffset() offset}.
	 * 
	 * @param drawer
	 *            an absolute drawer
	 */
	public void addAbsoluteDrawer(Drawer drawer) {
		absoluteBuffer.add(drawer);
	}

	/**
	 * Add a dynamic drawer. Its position is translated by the
	 * {@link #getOffset() offset}.
	 * 
	 * @param drawer
	 *            a dynamic drawer
	 */
	public void addDrawer(Drawer drawer) {
		buffer.add(drawer);
	}

	/**
	 * Add a static drawer. Its position is translated by the
	 * {@link #getOffset() offset}.
	 * 
	 * @param drawer
	 *            a static drawer
	 */
	public void addStaticDrawer(Drawer drawer) {
		statics.add(drawer);
	}

	/**
	 * When a drawer is added to this panel, it is not drawn immediately. First,
	 * the panel needs to be flushed using this method.
	 */
	public void flush() {
		absoluteDrawers.clear();
		absoluteDrawers.addAll(absoluteBuffer);
		absoluteBuffer.clear();
		drawers.clear();
		drawers.addAll(buffer);
		buffer.clear();
	}

	/**
	 * Returns the offset: the translation that is applied to each dynamic or
	 * static drawer.
	 * 
	 * @return the offset
	 */
	public Vector2D getOffset() {
		return offset;
	}

	/**
	 * Sets the dimensions of the game.
	 * 
	 * @param dimensions
	 *            a (width, height) dimensions vector
	 */
	public void setDimensions(Vector2D dimensions) {
		this.dimensions = dimensions;
	}

	/**
	 * Sets the translation that is applied to each dynamic or static drawer.
	 * 
	 * @param offset
	 *            the new offset to set
	 */
	public void setOffset(Vector2D offset) {
		this.offset = offset;
	}

	/**
	 * Sets the appropriate offset using the player's location, the (already
	 * known) dimensions and the actual dimensions of this panel.
	 * 
	 * @param location
	 *            the player's location
	 */
	public void setOffsetByPlayerLocation(Vector2D location) {
		double x = limit(-location.getX() + getWidth() / 2, -dimensions.getX() - 32 + getWidth(), 32);
		double y = limit(-location.getY() + getHeight() / 2, -dimensions.getY() - 32 + getHeight(), 32);
		setOffset(new Vector2D(x, y));
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

	private void drawDrawer(Graphics2D g, Drawer drawer) {
		int x = (int) (offset.getX());
		int y = (int) (offset.getY());
		g.translate(x, y);
		drawer.draw(g);
		g.translate(-x, -y);
	}

}
