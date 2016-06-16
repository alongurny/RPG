package rpg.element.entity;

import network.TcpClient;
import rpg.element.Depth;
import rpg.element.Element;
import rpg.element.entity.profession.Profession;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.Sprite;
import rpg.logic.level.Game;

/**
 * <p>
 * This class is a special one. It is the only class that can communicate with
 * the outer world because it has a reference to the client that is controlling
 * it. It represents this client in the game.
 * </p>
 * <p>
 * Each player has a field called {@link Profession profession}. The profession
 * determines most of its attributes, abilities, maximum health and mana, etc.
 * </p>
 * 
 * @author Alon
 *
 */
public class Player extends Entity {

	private int spriteCounter = 0;
	private TcpClient client;
	private double orientation;

	/**
	 * Constructs a new player using a client, a location and a profession.
	 * 
	 * @param client
	 *            the client that controls this player
	 * @param location
	 *            the initial location of this player
	 * @param profession
	 *            the profession of this player
	 */
	public Player(TcpClient client, Vector2D location, Profession profession) {
		super(location, profession);
		this.client = client;
		getAbilities().forEach(a -> a.setCooldown(0));
	}

	@Override
	public void act(Game game, double dt) {
		regenerate(dt);
		double vx = getVelocity().getX();
		if (vx != 0) {
			stepDraw(game);
			orientation = vx;
		}
	}

	/**
	 * Returns the client that controls this player in the game.
	 * 
	 * @return the client that controls this player in the game
	 */
	public TcpClient getClient() {
		return client;
	}

	@Override
	public Drawer getEntityDrawer() {
		if (orientation > 0) {
			return getProfession().getRightDrawer();
		}
		return getProfession().getLeftDrawer();
	}

	private double getHealthRegen() {
		return 0.02 * getAttribute(Attribute.CON);
	}

	@Override
	public Depth getDepth() {
		return Depth.HIGH;
	}

	private double getManaRegen() {
		return 0.02 * getAttribute(Attribute.INT);
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

	/**
	 * Sets only the velocity on the x axis. It will be a product of the given
	 * direction and the {@link #getSpeed() speed} of this player.
	 * 
	 * @param direction
	 *            either -1, 0 or 1: indicates the direction on the x axis
	 */
	public void moveHorizontally(double direction) {
		if (isAlive()) {
			setVelocity(new Vector2D(direction * getSpeed(), getVelocity().getY()));
		}
	}

	@Override
	public void onCollision(Game game, Element other) {
	}

	/**
	 * Tries to fall down. Will work only if this player is flying and is not
	 * resting above a platform.
	 * 
	 * @param game
	 *            the game
	 * @return <code>true</code> if this player
	 */
	public boolean tryFall(Game game) {
		if (isAlive() && hasEffect("flying") && !isRestingAboveSometing(game)) {
			setVelocity(new Vector2D(getVelocity().getX(), getSpeed()));
			return true;
		}
		return false;
	}

	/**
	 * Tries to fall down. Will work only if this player is flying or is resting
	 * above a platform.
	 * 
	 * @param game
	 *            the game
	 * @return <code>true</code> if this player
	 */
	public boolean tryJump(Game game) {
		if (isAlive() && (hasEffect("flying") || isRestingAboveSometing(game))) {
			setVelocity(new Vector2D(getVelocity().getX(), -getSpeed()));
			return true;
		}
		return false;
	}

	/**
	 * Returns <code>true</code> if the player rests above a platform,
	 * <code>false</code> otherwise
	 * 
	 * @param game
	 *            the game
	 * @return <code>true</code> if the player rests above a platform,
	 *         <code>false</code> otherwise
	 */
	public boolean isRestingAboveSometing(Game game) {
		return !game.getObstacles(this, getLocation().add(new Vector2D(0, 1))).isEmpty();
	}

	/**
	 * Regenerate health and mana for the player.
	 * 
	 * @param dt
	 *            the time passed. Determined by the <code>Game</code> method
	 *            {@link Game#update(double) update}.
	 */
	private void regenerate(double dt) {
		if (isAlive()) {
			addHealth(getHealthRegen() * dt);
			addMana(getManaRegen() * dt);
		}
	}

	private void stepDraw(Game game) {
		if (isRestingAboveSometing(game) && isAlive() && !hasEffect("disabled")) {
			spriteCounter++;
			if (spriteCounter >= 6) {
				Drawer drawer = getEntityDrawer();
				if (drawer instanceof Sprite) {
					Sprite sprite = (Sprite) drawer;
					sprite.step();
				}
				spriteCounter = 0;
			}
		}
	}

}