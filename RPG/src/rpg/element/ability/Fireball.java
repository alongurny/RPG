package rpg.element.ability;

import java.util.List;
import java.util.function.Supplier;

import rpg.Messages;
import rpg.ability.damage.DamageType;
import rpg.element.Depth;
import rpg.element.Element;
import rpg.element.entity.Entity;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.DrawIcon;
import rpg.graphics.Drawer;
import rpg.graphics.Rotate;
import rpg.logic.level.Game;

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
		return new Rotate(angle).andThen(new DrawIcon(Messages.getString("Fireball.img"), width, height)).andThen(new Rotate(-angle)); //$NON-NLS-1$
	}

	@Override
	public Depth getDepth() {
		return Depth.HIGH;
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
			entity.damage(damageSupplier.get(), DamageType.FIRE);
			game.removeDynamicElement(this);
		} else if (!other.isPassable(game, this)) {
			game.removeDynamicElement(this);
		}
	}

	@Override
	public void update(Game game, double dt) {
		List<Element> obstacles = game.getObstaclesFromMoveBy(this, velocity.multiply(dt));
		obstacles.forEach(obstacle -> {
			onCollision(game, obstacle);
			obstacle.onCollision(game, this);
		});
	}

}
