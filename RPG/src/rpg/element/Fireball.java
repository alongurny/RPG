package rpg.element;

import java.util.List;

import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.RotatedImageDrawer;
import rpg.graphics.draw.Drawer;
import rpg.logic.level.Level;

public class Fireball extends Element {

	private static int width = 32, height = 32;

	private static double defaultAngle = Math.toRadians(-90);
	private Entity caster;

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
	public Drawer getDrawer() {
		Vector2D direction = getVector("direction");
		double angle = Math.atan2(direction.getY(), direction.getX()) + defaultAngle;
		return new RotatedImageDrawer("img/fireball.gif", width, height, angle);
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-width / 2, -height / 2, width, height);
	}

	@Override
	public void update(Level level, double dt) {
		List<Element> obstacles = level.tryMoveBy(this, getVector("direction").multiply(getDouble("speed") * dt));
		obstacles.forEach(obstacle -> {
			onCollision(level, obstacle);
			obstacle.onCollision(level, this);
		});
	}

	@Override
	public void onCollision(Level level, Element other) {
		if (other instanceof Entity && caster != other) {
			Entity entity = (Entity) other;
			entity.remove("health", 10);
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
