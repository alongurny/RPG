package rpg.element.entity;

import rpg.element.Depth;
import rpg.element.Element;
import rpg.element.bonus.HealthPotion;
import rpg.element.entity.profession.DragonProfession;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.logic.level.Game;

public class Dragon extends Entity {

	private Vector2D prevVelocity;

	public Dragon(Vector2D location) {
		super(location, new DragonProfession());
		setVelocity(new Vector2D(-getSpeed(), 0));
	}

	@Override
	public void act(Game game, double dt) {
		if (getVelocity().getMagnitude() > 0) {
			prevVelocity = getVelocity();
			if (!game.getObstacles(this, getLocation().add(getVelocity().getUnitalVector().multiply(8))).isEmpty()) {
				setVelocity(getVelocity().negate());
			}
		} else {
			setVelocity(prevVelocity);
		}
		if (isAlive()) {
			setTarget(game.getDynamicElements().stream().filter(p -> p instanceof Player)
					.sorted((a, b) -> Double.compare(Element.distance(a, this), Element.distance(b, this)))
					.findFirst());
			tryCast(game, 0);
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
		return new Rectangle(-60, -70, 120, 140);
	}

	@Override
	public boolean isFriendly(Entity entity) {
		return entity == this;
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
