package rpg.element;

import java.util.Optional;

import rpg.element.entity.Profession;
import rpg.element.entity.Race;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.DrawIcon;
import rpg.graphics.Drawer;
import rpg.logic.level.Level;

public class Dragon extends Entity {
	private static int width = 32, height = 32;

	private Drawer drawer;

	public Dragon(Vector2D location) {
		super(location, Race.DRAGON, Profession.DRAGON);
		drawer = new DrawIcon("img/dragon.png", 32, 32);
	}

	@Override
	public void act(Level level, double dt) {
		if (isAlive()) {
			level.getDynamicElements().stream().filter(p -> p instanceof Player).findFirst()
					.ifPresent(p -> tryCast(level, 0, Optional.of(p)));
		} else {
			level.addDynamicElement(new HealthPotion(getLocation()));
			level.removeDynamicElement(this);
			level.finish();
		}
	}

	@Override
	protected Drawer getEntityDrawer() {
		return drawer;
	}

	@Override
	public int getIndex() {
		return 3;
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-width / 2, -height / 2, width, height);
	}

	@Override
	public boolean isFriendly(Entity entity) {
		return entity == this;
	}

	@Override
	public boolean isPassable(Level level, Element other) {
		if (other instanceof Entity) {
			return false;
		}
		return true;
	}

	@Override
	public void onCollision(Level level, Element other) {

	}

}
