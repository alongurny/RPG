package rpg.element.entity;

import rpg.element.Depth;
import rpg.element.Element;
import rpg.element.bonus.HealthPotion;
import rpg.element.entity.profession.MiniDragonProfession;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.logic.level.Game;

public class MiniDragon extends Entity {

	public MiniDragon(Vector2D location) {
		super(location, new MiniDragonProfession());
		setVelocity(new Vector2D(-getSpeed(), 0));
	}

	@Override
	public void act(Game game, double dt) {
		if (getVelocity().getMagnitude() >= 32) {
			setVelocity(getVelocity().getUnitalVector().multiply(32));
		}
		if (isAlive()) {
			setTarget(game.getDynamicElements().stream().filter(p -> p instanceof Player)
					.sorted((a, b) -> Double.compare(Element.distance(a, this), Element.distance(b, this)))
					.findFirst());
			if (!tryCast(game, 1) && getTarget().filter(t -> Element.distance(this, t) > 240).isPresent()) {
				getTarget().ifPresent(t -> {
					if (!t.getLocation().equals(getLocation())) {
						setAcceleration(t.getLocation().subtract(this.getLocation()).getUnitalVector().multiply(10));
					}
				});
			}
		} else {
			game.addDynamicElement(new HealthPotion(getLocation()));
			game.removeDynamicElement(this);
		}
	}

	@Override
	public Depth getDepth() {
		return Depth.HIGH;
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-20, -23, 40, 50);
	}

	@Override
	public boolean isFriendly(Entity entity) {
		return !(entity instanceof Player);
	}

	@Override
	public boolean isPassable(Game game, Element other) {
		return true;
	}

	@Override
	public void onCollision(Game game, Element other) {

	}

	@Override
	protected Drawer getEntityDrawer() {
		return getVelocity().getX() > 0 ? getProfession().getRightDrawer() : getProfession().getLeftDrawer();
	}

}
