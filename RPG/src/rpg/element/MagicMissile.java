package rpg.element;

import java.util.List;
import java.util.function.Supplier;

import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.TileDrawer;
import rpg.logic.level.Level;

public class MagicMissile extends Element {

	private static int width = 32, height = 32;

	private Entity caster;
	private Entity target;
	private Supplier<Double> damageSupplier;
	private double speed;

	public MagicMissile(Entity caster, Vector2D location, double speed, Entity target,
			Supplier<Double> damageSupplier) {
		super(location);
		this.caster = caster;
		this.target = target;
		this.speed = speed;
		this.damageSupplier = damageSupplier;
	}

	@Override
	public Drawer getDrawer() {
		return TileDrawer.sprite(0, 6, 34, 36);
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-width / 2, -height / 2, width, height);
	}

	@Override
	public void update(Level level, double dt) {
		Vector2D v = target.getLocation().subtract(getLocation());
		if (v.getMagnitude() > 0) {
			v = v.getUnitalVector().multiply(speed * dt);
		}
		List<Element> obstacles = level.tryMoveBy(this, v);
		obstacles.forEach(obstacle -> {
			onCollision(level, obstacle);
			obstacle.onCollision(level, this);
		});
	}

	@Override
	public void onCollision(Level level, Element other) {
		if (other instanceof Entity && caster != other) {
			Entity entity = (Entity) other;
			entity.subtractHealth(damageSupplier.get());
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
