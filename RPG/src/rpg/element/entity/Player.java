package rpg.element.entity;

import java.awt.Graphics;

import rpg.element.Element;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Sprite;
import rpg.graphics.Tileset;
import rpg.logic.level.Level;

public class Player extends Entity {

	private Sprite northSprite, southSprite, westSprite, eastSprite;

	public Player(Vector2D location, Race race) {
		super(location, race);
		northSprite = Sprite.get(Tileset.get(1), 3, 0, 1, 2);
		westSprite = Sprite.get(Tileset.get(1), 1, 0, 1, 2);
		southSprite = Sprite.get(Tileset.get(1), 0, 0, 1, 2);
		eastSprite = Sprite.get(Tileset.get(1), 2, 0, 1, 2);
	}

	@Override
	public void drawEntity(Graphics g) {
		double x = getVector("direction").getX();
		double y = getVector("direction").getY();
		if (x == 0 && y > 0) {
			southSprite.draw(g);
		} else if (x == 0 && y < 0) {
			northSprite.draw(g);
		} else if (x > 0 && y == 0) {
			eastSprite.draw(g);
		} else if (x < 0 && y == 0) {
			westSprite.draw(g);
		}
	}

	@Override
	public void onCollision(Level level, Element other) {
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-16, -16, 32, 32);
	}

	@Override
	public int getIndex() {
		return 10;
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
		if (!level.tryMoveBy(this, getVelocity().multiply(dt)).isEmpty()
				&& !level.tryMoveBy(this, new Vector2D(getVelocity().getX() * dt, 0)).isEmpty()) {
			level.tryMoveBy(this, new Vector2D(0, getVelocity().getY() * dt));
		}
		addBarValue("health", dt * getTotalNumber("healthRegen"));
		addBarValue("mana", dt * getTotalNumber("manaRegen"));
	}

	@Override
	public void onDeath(Level level) {
	}

	public Vector2D getVelocity() {
		return getTotalVector("velocityDirection").multiply(getTotalNumber("speed"));
	}

}