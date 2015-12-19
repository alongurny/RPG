package rpg.element;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;

import rpg.Interactive;
import rpg.element.entity.Entity;
import rpg.logic.Level;
import rpg.physics.Vector2D;
import rpg.ui.Rectangle;

public class Portal extends Element implements Interactive {

	private static Image image;
	private static int width, height;

	static {
		try {
			image = new ImageIcon(new File("img/portal.gif").toURI().toURL()).getImage();
			width = 32;
			height = 32;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public static Portal[] getPair(Vector2D v1, Vector2D v2) {
		Portal p1 = new Portal(v1, v2);
		Portal p2 = new Portal(v2, v1);
		return new Portal[] { p1, p2 };
	}

	private Vector2D target;

	public Portal(Vector2D location, Vector2D target) {
		super(location);
		this.target = target;
	}

	@Override
	public void update(Level level, double dt) {

	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, -width / 2, -height / 2, width, height, null);
	}

	@Override
	public void onCollision(Level level, Element other) {

	}

	@Override
	public int getIndex() {
		return 1;
	}

	@Override
	public void onInteract(Level level, Entity entity) {
		level.tryMove(entity, target.add(entity.getLocation()).subtract(getLocation()));
	}

	@Override
	public boolean isInteractable(Level level, Entity entity) {
		Rectangle entityRect = entity.getAbsoluteRect();
		Rectangle myRect = this.getAbsoluteRect();
		Rectangle intersect = Rectangle.intersect(entityRect, myRect);
		return intersect.getWidth() >= 0.6 * entityRect.getWidth()
				&& intersect.getHeight() >= 0.6 * entityRect.getHeight();
	}

	@Override
	public boolean isPassable(Level level, Element other) {
		return true;
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-width / 2, -height / 2, width, height);
	}

}
