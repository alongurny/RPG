package rpg.element;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import rpg.element.entity.Entity;
import rpg.level.Level;
import rpg.physics.Vector2D;

public class Fireball extends DynamicElement {

	private Entity caster;

	public Fireball(Entity caster, Vector2D location, Vector2D direction, double speed) {
		super(location);
		this.direction = direction;
		this.speed = speed;
		this.caster = caster;
	}

	private static BufferedImage image;
	public static int width, height;

	private static double defaultAngle = Math.toRadians(-90);
	private Vector2D direction;
	private double speed;

	static {
		try {
			image = ImageIO.read(new File("img/fireball.gif"));

			width = image.getWidth(null) / 2;
			height = image.getHeight(null) / 2;
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
	public void update(Level level) {
		if (!level.tryMoveBy(this, direction.getUnitalVector().multiply(speed))) {
			level.removeElement(this);
		}
	}

	@Override
	public void onCollision(Level level, DynamicElement other) {
		if (other instanceof Entity && other != caster) {
			Entity entity = (Entity) other;
			entity.removeBarValue(10, "health");
			level.removeElement(this);
		} else if (!other.isPassable(level, this)) {
			level.removeElement(this);
		}
	}

	@Override
	public int getIndex() {
		return 100;
	}

	@Override
	public boolean isPassable(Level level, DynamicElement other) {
		return true;
	}

}
