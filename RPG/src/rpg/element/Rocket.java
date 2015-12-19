package rpg.element;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import rpg.logic.Level;
import rpg.physics.Vector2D;

public class Rocket extends Element {

	public Rocket(Vector2D location, Vector2D direction, double speed) {
		super(location);
		this.direction = direction;
		this.speed = speed;
	}

	private static BufferedImage image;
	public static int width, height;

	private static double defaultAngle = Math.toRadians(90);
	private Vector2D direction;
	private double speed;

	static {
		try {
			image = ImageIO.read(new File("img/rocket.png"));

			width = 20;
			height = 20;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics g) {
		double theta = Math.atan2(direction.getY(), direction.getX());
		AffineTransform at = new AffineTransform();
		at.rotate(theta + defaultAngle, image.getWidth(null) / 2, image.getHeight(null) / 2);
		AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		g.drawImage(op.filter(image, null), -width / 2, -height / 2, width, height, null);
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-width / 2, -height / 2, width, height);
	}

	@Override
	public void update(Level level, double dt) {
		Vector2D v = direction.getUnitalVector().multiply(speed * dt);
		List<Element> obstacles = level.tryMoveBy(this, v);
		if (!obstacles.isEmpty()) {
			for (Element obstacle : obstacles) {
				if (obstacle instanceof Block) {
					level.removeStaticElement(obstacle);
				}
			}
			level.removeDynamicElement(this);
		}

	}

	@Override
	public void onCollision(Level level, Element other) {
		if (other instanceof Block) {
			Block b = (Block) other;
			level.getMap().add(new Air(b.getLocation()));
			level.removeDynamicElement(this);
		}
	}

	@Override
	public int getIndex() {
		return 100;
	}

	@Override
	public boolean isPassable(Level level, Element other) {
		return true;
	}

}
