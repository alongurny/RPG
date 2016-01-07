package rpg.element.entity;

import java.awt.Graphics;

import rpg.element.Element;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Sprite;
import rpg.graphics.Tileset;
import rpg.logic.level.Level;

public class Player extends Entity {

	private Sprite[] sprites;
	private static final int NORTH = 3, SOUTH = 0, WEST = 1, EAST = 2;

	public Player(Vector2D location, Race race) {
		super(location, race);
		sprites = new Sprite[4];
		for (int i = 0; i < sprites.length; i++) {
			sprites[i] = Sprite.get(Tileset.get(1), i, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2);
		}
		set("spriteNumber", NORTH);
		set("spriteCounter", 0);
	}

	public Player(Vector2D location, String race) {
		this(location, Race.valueOf(race));
	}

	@Override
	public void drawEntity(Graphics g) {
		sprites[getInteger("spriteNumber")].draw(g, getInteger("spriteCounter"));
	}

	public void step() {

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
		Vector2D velocity = getVelocity().multiply(dt);
		double x = velocity.getX();
		double y = velocity.getY();
		if (!level.tryMoveBy(this, velocity).isEmpty()) {
			if (level.tryMoveBy(this, new Vector2D(0, y)).isEmpty()) {
				x = 0;
			} else if (level.tryMoveBy(this, new Vector2D(x, 0)).isEmpty()) {
				y = 0;
			} else {
				x = 0;
				y = 0;
			}
		}
		addBarValue("health", dt * getTotalNumber("healthRegen"));
		addBarValue("mana", dt * getTotalNumber("manaRegen"));
		if (y > 0) {
			set("spriteNumber", SOUTH);
		} else if (y < 0) {
			set("spriteNumber", NORTH);
		} else if (x > 0) {
			set("spriteNumber", EAST);
		} else if (x < 0) {
			set("spriteNumber", WEST);
		} else {
			return;
		}
		set("spriteCounter", (getInteger("spriteCounter") + 1) % 12);
	}

	@Override
	public void onDeath(Level level) {
	}

	public Vector2D getVelocity() {
		return getTotalVector("velocityDirection").multiply(getTotalNumber("speed"));
	}

}