package rpg.element.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import rpg.element.Element;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.logic.Level;

public class Player extends Entity {

	private static Image playerImage;
	private static final String PLAYER_IMAGE_LOCATION = "img/player.png";
	private static int width, height;

	static {
		try {
			playerImage = ImageIO.read(new File(PLAYER_IMAGE_LOCATION));
			width = 30;
			height = 30;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Player(Vector2D location, Race race) {
		super(location, race);
	}

	@Override
	public void drawEntity(Graphics g) {
		g.drawImage(playerImage, -width / 2, -height / 2, width, height, null);
	}

	@Override
	public void onCollision(Level level, Element other) {
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-width / 2, -height / 2, width, height);
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