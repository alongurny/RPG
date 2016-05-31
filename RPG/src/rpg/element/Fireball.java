package rpg.element;

import java.util.List;

import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.RotatedImageDrawer;
import rpg.logic.level.Level;

public class Fireball extends Element {

	private static int width = 32, height = 32;

	private static double defaultAngle = Math.toRadians(-90);
	private Entity caster;
	private Vector2D velocity;

	public Fireball(Entity caster, Vector2D location, Vector2D velocity) {
		super(location);
		this.caster = caster;
		this.velocity = velocity;
	}

	@Override
	public Drawer getDrawer() {
		double angle = Math.atan2(velocity.getY(), velocity.getX()) + defaultAngle;
		return new RotatedImageDrawer("img/fireball.gif", width, height, angle);
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
			entity.subtractHealth(10);
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
