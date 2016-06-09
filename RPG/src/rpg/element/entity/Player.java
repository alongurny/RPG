package rpg.element.entity;

import network.TcpClient;
import rpg.element.Depth;
import rpg.element.Element;
import rpg.element.entity.profession.Profession;
import rpg.element.entity.race.Race;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.Sprite;
import rpg.graphics.TileDrawer;
import rpg.logic.level.Game;

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

	public TcpClient getClient() {
		return client;
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
	public Depth getDepth() {
		return Depth.HIGH;
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

	public void moveHorizontally(double direction) {
		setVelocity(new Vector2D(direction * getSpeed(), getVelocity().getY()));
	}

	@Override
	public void onCollision(Game game, Element other) {
	}

	public boolean tryFall(Game game) {
		if (hasEffect("flying") && !isRestingAboveSometing(game)) {
			setVelocity(new Vector2D(getVelocity().getX(), getSpeed()));
			return true;
		}
		return false;
	}

	public boolean tryJump(Game game) {
		if (hasEffect("flying") || isRestingAboveSometing(game)) {
			setVelocity(new Vector2D(getVelocity().getX(), -getSpeed()));
			return true;
		}
		return false;
	}

	public boolean isRestingAboveSometing(Game game) {
		return !game.getObstacles(this, getLocation().add(new Vector2D(0, 1))).isEmpty();
	}

	private void regenerate(double dt) {
		if (isAlive()) {
			addHealth(getHealthRegen() * dt);
			addMana(getManaRegen() * dt);
		}
	}

	private void stepDraw() {
		if (Math.abs(getVelocity().getY()) < 1 && isAlive() && !hasEffect("disabled")) {
			counter++;
			if (counter >= 32) {
				eastDrawer.step();
				westDrawer.step();
				counter = 0;
			}
		}
	}

}