package rpg.graphics;

import java.awt.Graphics2D;

/**
 * This class is a base class for many classes that most of them are wrappers
 * for {@link Graphics2D} methods. For example, {@link DrawString} wraps
 * {@link Graphics2D#drawString(String, int, int) drawString}.
 * 
 * @author Alon
 *
 */
public abstract class Drawer {

	protected Drawer() {
	}

	/**
	 * Returns a new drawer that will first draw this one and then the other
	 * one.
	 * 
	 * @param other
	 *            another drawer
	 * @return a concatenation of the drawers
	 */
	public Drawer andThen(Drawer other) {
		Drawer obj = this;
		return new Drawer() {

			@Override
			public void draw(Graphics2D g) {
				obj.draw(g);
				other.draw(g);
			}

			@Override
			public String represent() {
				return obj.represent() + ";;" + other.represent();
			}
		};
	}

	/**
	 * Draws on the graphics.
	 * 
	 * @param g
	 *            the graphics to draw
	 */
	public abstract void draw(Graphics2D g);

	@Override
	public String toString() {
		return represent();
	}

	/**
	 * Returns a string representation of this drawer, that later will be used
	 * to reconstruct it on the client side.
	 * 
	 * @return a string representation of this drawer
	 */
	protected abstract String represent();

}
