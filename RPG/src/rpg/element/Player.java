package rpg.element;

import rpg.element.entity.Attribute;
import rpg.element.entity.Profession;
import rpg.element.entity.Race;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.Sprite;
import rpg.graphics.TileDrawer;
import rpg.logic.level.Game;
import tcp.TcpClient;

public class Player extends Entity {

	private Sprite southDrawer = TileDrawer.sprite(1, 0, 0, 2);
	private Sprite westDrawer = TileDrawer.sprite(1, 1, 0, 2);
	private Sprite eastDrawer = TileDrawer.sprite(1, 2, 0, 2);

	private int counter = 0;
	private TcpClient client;
	private double orientation;

	public Player(TcpClient client, Vector2D location, Race race, Profession profession) {
		super(location, race, profession);
		this.client = client;
		setAcceleration(new Vector2D(0, 100));
	}

	@Override
	public void act(Game game, double dt) {
		regenerate(dt);
		if (getVelocity().getX() != 0) {
			stepDraw();
			orientation = getVelocity().getX();
		}
	}

	@Override
	public Drawer getEntityDrawer() {
		if (orientation > 0) {
			return eastDrawer;
		}
		if (orientation < 0) {
			return westDrawer;
		}
		return southDrawer;
	}

	public double getHealthRegen() {
		return 0.05 * getAttribute(Attribute.CON);
	}

	@Override
	public int getIndex() {
		return 1000;
	}

	public double getManaRegen() {
		return 0.03 * getAttribute(Attribute.INT);
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-9, -15, 18, 30);
	}

	@Override
	public boolean isFriendly(Entity other) {
		return other == this;
	}

	@Override
	public boolean isPassable(Game game, Element other) {
		return true;
	}

	@Override
	public void onCollision(Game game, Element other) {
	}

	private void regenerate(double dt) {
		if (isAlive()) {
			addHealth(getHealthRegen() * dt);
			addMana(getManaRegen() * dt);
		}
	}

	public boolean tryJump() {
		// FIXME should be == 0
		if (Math.abs(getVelocity().getY()) < 1) {
			setVelocity(new Vector2D(getVelocity().getX(), -getSpeed()));
			return true;
		}
		return false;
	}

	public void moveHorizontally(double direction) {
		setVelocity(new Vector2D(direction * getSpeed(), getVelocity().getY()));
	}

	private void stepDraw() {
		if (Math.abs(getVelocity().getY()) < 1 && isAlive()
				&& !getEffects().stream().anyMatch(e -> e.getKey().equals("disabled"))) {
			counter++;
			if (counter >= 32) {
				eastDrawer.step();
				westDrawer.step();
				counter = 0;
			}
		}
	}

	public TcpClient getClient() {
		return client;
	}

}