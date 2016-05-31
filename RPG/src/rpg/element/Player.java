package rpg.element;

import rpg.element.entity.Race;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.IconDrawer;
import rpg.graphics.draw.Drawer;
import rpg.logic.level.Level;

public class Player extends Entity {

	private Drawer drawer = new IconDrawer("img/player.png", 32, 32);

	public Player(Vector2D location, Race race) {
		super(location, race);
	}

	public Player(Vector2D location, String race) {
		this(location, Race.valueOf(race));
	}

	@Override
	public Drawer getEntityDrawer() {
		return drawer;
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
		return !(other instanceof Entity);
	}

	private void regenerate(double dt) {
		if (isAlive()) {
			add("health", dt * getTotalNumber("healthRegen"));
			add("mana", dt * getTotalNumber("manaRegen"));
		}
	}

	private void move(Level level, double dt) {
		if (isAlive() && !getTotalBoolean("disabled")) {
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
		}
	}

	@Override
	public void act(Level level, double dt) {
		regenerate(dt);
		move(level, dt);
	}

	public Vector2D getVelocity() {
		return getTotalVector("velocityDirection").multiply(getTotalNumber("speed"));
	}

}