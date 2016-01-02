package rpg.element;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import rpg.element.entity.Entity;
import rpg.logic.Level;
import rpg.physics.Vector2D;
import rpg.ui.Rectangle;

public class Fireball extends Element {

	private static BufferedImage image;
	public static int width, height;

	private static double defaultAngle = Math.toRadians(-90);

	static {
		try {
			image = ImageIO.read(new File("img/fireball.gif"));
			width = image.getWidth(null) / 2;
			height = image.getHeight(null) / 2;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Fireball(Vector2D location, Vector2D direction, double speed) {
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
		if (!level.tryMoveBy(this, getVector("direction").multiply(getNumber("speed") * dt)).isEmpty()) {
			level.removeDynamicElement(this);
		}
	}

	@Override
	public void onCollision(Level level, Element other) {
		if (other instanceof Entity) {
			Entity entity = (Entity) other;
			entity.removeBarValue("health", 10);
			level.removeDynamicElement(this);
		} else if (!other.isPassable(level, this)) {
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
