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

	public Player(TcpClient client, Vector2D location, Race race, Profession profession) {
		super(location, race, profession);
		this.client = client;
		setAcceleration(new Vector2D(0, 10));
	}

	@Override
	public void act(Game game, double dt) {
		regenerate(dt);
		move(game, dt);
		if (getVelocity().getY() == 0) {
			setVelocity(new Vector2D(0,0));
		}
	}

	@Override
	public Drawer getEntityDrawer() {
		if (getOrientation().getX() > 0) {
			return eastDrawer;
		}
		if (getOrientation().getX() < 0) {
			return westDrawer;
		}
		return southDrawer;
	}

	public double getHealthRegen() {
		return 0.05 * getAttribute(Attribute.CON);
	}

	@Override
	public int getIndex() {
		return 10;
	}

	public double getManaRegen() {
		return 0.03 * getAttribute(Attribute.INT);
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-14, -14, 28, 28);
	}

	@Override
	public boolean isFriendly(Entity other) {
		return other instanceof Player;
	}

	@Override
	public boolean isPassable(Game game, Element other) {
		return !(other instanceof Entity);
	}

	private void move(Game game, double dt) {
		if (isAlive() && !getEffects().stream().anyMatch(e -> e.getKey().equals("disabled"))) {
			stepDraw();
		}
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

	public void step() {

	}

	private void stepDraw() {
		counter++;
		if (counter >= 32) {
			eastDrawer.step();
			westDrawer.step();
			counter = 0;
		}
	}

	public TcpClient getClient() {
		return client;
	}

}