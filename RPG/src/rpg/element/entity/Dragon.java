package rpg.element.entity;

import rpg.element.Element;
import rpg.element.bonus.HealthPotion;
import rpg.element.entity.profession.DragonProfession;
import rpg.element.entity.race.DragonRace;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.DrawIcon;
import rpg.graphics.Drawer;
import rpg.logic.level.Game;

public class Dragon extends Entity {
	private static int width = 32, height = 32;

	private Drawer drawer;

	public Dragon(Vector2D location) {
		super(location, new DragonRace(), new DragonProfession());
		drawer = new DrawIcon("img/dragon.png", 32, 32);
	}

	@Override
	public void act(Game game, double dt) {
		if (isAlive()) {
			game.getDynamicElements().stream().filter(p -> p instanceof Player).findFirst()
					.ifPresent(p -> tryCast(game, 0));
		} else {
			game.addDynamicElement(new HealthPotion(getLocation()));
			game.removeDynamicElement(this);
			game.finish();
		}
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
	public boolean isPassable(Game game, Element other) {
		if (other instanceof Entity) {
			return false;
		}
		return true;
	}

	@Override
	public void onCollision(Game game, Element other) {

	}

	@Override
	protected Drawer getEntityDrawer() {
		return drawer;
	}

}
