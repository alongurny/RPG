package rpg.element;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import rpg.element.entity.DisabledEffect;
import rpg.element.entity.Entity;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.logic.level.Level;

public class Fireball extends Element {

	private static BufferedImage image;
	public static int width, height;

	private static double defaultAngle = Math.toRadians(-90);
	private Entity caster;

	static {
		try {
			image = ImageIO.read(new File("img/fireball.gif"));
			width = image.getWidth(null) / 2;
			height = image.getHeight(null) / 2;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Fireball(Entity caster, Vector2D location, Vector2D direction, double speed) {
		super(location);
		this.caster = caster;
		set("direction", direction.getUnitalVector());
		set("speed", speed);
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
		if (!level.tryMoveBy(this, getVector("direction").multiply(getDouble("speed") * dt))) {
			level.removeDynamicElement(this);
		}
	}

	@Override
	public void onCollision(Level level, Element other) {
		if (other instanceof Entity && caster != other) {
			Entity entity = (Entity) other;
			entity.remove("health", 10);
			entity.addEffect(new DisabledEffect(0.5));
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
