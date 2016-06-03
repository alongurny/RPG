package rpg.element;

import java.util.List;
import java.util.function.Supplier;

import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.DrawIcon;
import rpg.graphics.Drawer;
import rpg.graphics.Rotate;
import rpg.logic.level.Level;

public class Fireball extends Element {

	private static int width = 32, height = 32;

	private static double defaultAngle = Math.toRadians(-90);
	private Entity caster;
	private Vector2D velocity;
	private Supplier<Double> damageSupplier;

	public Fireball(Entity caster, Vector2D location, Vector2D velocity, Supplier<Double> damageSupplier) {
		super(location);
		this.caster = caster;
		this.velocity = velocity;
		this.damageSupplier = damageSupplier;
	}

	@Override
	public Drawer getDrawer() {
		double angle = Math.atan2(velocity.getY(), velocity.getX()) + defaultAngle;
		return new Rotate(angle).andThen(new DrawIcon("img/fireball.gif", width, height)).andThen(new Rotate(-angle));
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-width / 2, -height / 2, width, height);
	}

	@Override
	public void update(Level level, double dt) {
		List<Element> obstacles = level.tryMoveBy(this, velocity.multiply(dt));
		obstacles.forEach(obstacle -> {
			onCollision(level, obstacle);
			obstacle.onCollision(level, this);
		});
	}

	@Override
	public void onCollision(Level level, Element other) {
		if (other instanceof Entity && caster != other) {
			Entity entity = (Entity) other;
			entity.damage(damageSupplier.get(), DamageType.FIRE);
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
