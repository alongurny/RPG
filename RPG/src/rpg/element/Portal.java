package rpg.element;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;

import rpg.Interactive;
import rpg.element.entity.Entity;
import rpg.level.Level;

public class Portal extends StaticElement implements Interactive {

	private static Image image;
	private static int width, height;

	static {
		try {
			image = new ImageIcon(new File("img/portal.gif").toURI().toURL()).getImage();
			width = 64;
			height = 64;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public static Portal[] getPair(int y1, int x1, int y2, int x2) {
		Portal p1 = new Portal(y1, x1, y2, x2);
		Portal p2 = new Portal(y2, x2, y1, x1);
		return new Portal[] { p1, p2 };
	}

	private int yTarget, xTarget;

	public Portal(int y, int x, int yTarget, int xTarget) {
		super(y, x, 2, 2);
		this.yTarget = yTarget;
		this.xTarget = xTarget;
	}

	@Override
	public void update(Level level) {

	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, 0, 0, width, height, null);
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
		level.tryMove(entity, StaticElement.getLocation(level, yTarget, xTarget).add(entity.getLocation())
				.subtract(StaticElement.getLocation(level, getRow(), getColumn())));
	}

	@Override
	public boolean isInteractable(Level level, Entity entity) {
		Rectangle entityRect = entity.getAbsoluteRect();
		Rectangle myRect = StaticElement.getRect(level, this);
		Rectangle intersect = entityRect.intersection(myRect);
		return intersect.width >= 0.6 * entityRect.width && intersect.height >= 0.6 * entityRect.height;
	}

	@Override
	public boolean isPassable(Level level, Element other) {
		return true;
	}

}
