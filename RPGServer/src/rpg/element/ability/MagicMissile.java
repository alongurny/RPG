package rpg.element.ability;

import java.util.List;
import java.util.function.Supplier;

import external.Messages;
import rpg.ability.damage.DamageType;
import rpg.element.Depth;
import rpg.element.Element;
import rpg.element.entity.Entity;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.logic.level.Game;

public class MagicMissile extends Element {

	private static int width = 32, height = 32;

	private Entity caster;
	private Entity target;
	private Supplier<Double> damageSupplier;
	private double speed;
	private Drawer drawer;

	public MagicMissile(Entity caster, Vector2D location, double speed, Entity target,
			Supplier<Double> damageSupplier) {
		super(location);
		this.caster = caster;
		this.target = target;
		this.speed = speed;
		this.damageSupplier = damageSupplier;
		this.drawer = Messages.getTileDrawer("MagicMissile");
	}

	@Override
	public Drawer getDrawer() {
		return drawer;
	}

	@Override
	public Depth getDepth() {
		return Depth.MEDIUM;
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-width / 2, -height / 2, width, height);
	}

	@Override
	public boolean isPassable(Game game, Element other) {
		return true;
	}

	@Override
	public void onCollision(Game game, Element other) {
		if (other instanceof Entity && caster != other) {
			Entity entity = (Entity) other;
			entity.damage(damageSupplier.get(), DamageType.FORCE);
			game.removeDynamicElement(this);
			caster.addXP(100);
		} else if (!other.isPassable(game, this)) {
			game.removeDynamicElement(this);
		}
	}

	@Override
	public void update(Game game, double dt) {
		Vector2D v = target.getLocation().subtract(getLocation());
		if (v.getMagnitude() > 0) {
			v = v.getUnitalVector().multiply(speed * dt);
		}
		List<Element> obstacles = game.getObstaclesFromMoveBy(this, v);
		obstacles.forEach(obstacle -> {
			onCollision(game, obstacle);
			obstacle.onCollision(game, this);
		});
	}

}
