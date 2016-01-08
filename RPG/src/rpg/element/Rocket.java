package rpg.element;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.logic.level.Level;

public class Rocket extends Element {

	private static BufferedImage image;
	public static int width, height;

	private static double defaultAngle = Math.toRadians(90);

	static {
		try {
			image = ImageIO.read(new File("img/rocket.png"));
			width = 20;
			height = 20;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Rocket(Vector2D location, Vector2D direction, double speed) {
		super(location);
		set("direction", direction.getUnitalVector());
		set("speed", speed);
	}

	@Override
	public void draw(Graphics g) {
		Vector2D direction = getVector("direction");
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
		Vector2D v = getVector("direction").multiply(getDouble("speed") * dt);
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
