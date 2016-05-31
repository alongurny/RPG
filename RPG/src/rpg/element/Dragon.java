package rpg.element;

import rpg.ability.FireballSpell;
import rpg.element.entity.Race;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.IconDrawer;
import rpg.graphics.draw.Drawer;
import rpg.logic.level.Level;

public class Dragon extends Entity {
	private static int width = 32, height = 32;

	private Drawer drawer;

	public Dragon(Vector2D location) {
		super(location, Race.DRAGON);
		addAbility(new FireballSpell());
		setLimited("mana", 100, 0, 100);
		drawer = new IconDrawer("img/dragon.png", 32, 32);
	}

	@Override
	protected Drawer getEntityDrawer() {
		return drawer;
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-width / 2, -height / 2, width, height);
	}

	@Override
	public int getIndex() {
		return 3;
	}

	@Override
	public void onCollision(Level level, Element other) {

	}

	@Override
	public boolean isPassable(Level level, Element other) {
		if (other instanceof Entity) {
			return false;
		}
		return true;
	}

	@Override
	public void act(Level level, double dt) {
		if (isAlive()) {
			for (Element e : level.getDynamicElements()) {
				if (e instanceof Player) {
					tryCast(level, 0, e);
				}
			}
		} else {
			level.addDynamicElement(new HealthPotion(getLocation()));
			level.removeDynamicElement(this);
			level.finish();
		}
	}

}
