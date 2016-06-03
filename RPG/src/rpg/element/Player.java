package rpg.element;

import rpg.element.entity.Attribute;
import rpg.element.entity.Profession;
import rpg.element.entity.Race;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.Sprite;
import rpg.graphics.TileDrawer;
import rpg.logic.level.Level;

public class Player extends Entity {

	private Sprite northDrawer = TileDrawer.sprite(1, 3, 0, 2);
	private Sprite southDrawer = TileDrawer.sprite(1, 0, 0, 2);
	private Sprite westDrawer = TileDrawer.sprite(1, 1, 0, 2);
	private Sprite eastDrawer = TileDrawer.sprite(1, 2, 0, 2);

	private int counter = 0;

	public Player(Vector2D location, Race race, Profession profession) {
		super(location, race, profession);
	}

	@Override
	public Drawer getEntityDrawer() {
		if (getOrientation().getX() > 0) {
			return eastDrawer;
		}
		if (getOrientation().getX() < 0) {
			return westDrawer;
		}
		if (getOrientation().getY() < 0) {
			return northDrawer;
		}
		return southDrawer;
	}

	public void step() {

	}

	@Override
	public void onCollision(Level level, Element other) {
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-14, -14, 28, 28);
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
			addHealth(getHealthRegen() * dt);
			addMana(getManaRegen() * dt);
		}
	}

	public double getManaRegen() {
		return 0.01 * getAttribute(Attribute.INT);
	}

	public double getHealthRegen() {
		return 0.01 * getAttribute(Attribute.CON);
	}

	private void move(Level level, double dt) {
		if (isAlive() && !getEffects().stream().anyMatch(e -> e.getKey().equals("disabled"))) {
			Vector2D d = getVelocity().multiply(dt);
			if (!d.equals(Vector2D.ZERO)) {
				double x = d.getX();
				double y = d.getY();
				if (level.tryMoveBy(this, d).isEmpty()) {
					stepDraw();
					setOrientation((x != 0 ? new Vector2D(x, 0) : new Vector2D(0, y)).getUnitalVector());
				} else if (level.tryMoveBy(this, new Vector2D(x, 0)).isEmpty() && x != 0) {
					stepDraw();
					setOrientation(new Vector2D(x, 0).getUnitalVector());
				} else if (level.tryMoveBy(this, new Vector2D(0, y)).isEmpty() && y != 0) {
					stepDraw();
					setOrientation(new Vector2D(0, y).getUnitalVector());
				}
			}
		}
	}

	private void stepDraw() {
		counter++;
		if (counter >= 32) {
			eastDrawer.step();
			westDrawer.step();
			northDrawer.step();
			southDrawer.step();
			counter = 0;
		}
	}

	@Override
	public void act(Level level, double dt) {
		regenerate(dt);
		move(level, dt);
	}

}