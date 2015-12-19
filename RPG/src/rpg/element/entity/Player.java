package rpg.element.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import rpg.element.Element;
import rpg.level.Level;
import rpg.physics.Vector2D;

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

	private Vector2D velocity;
	private Profession profession;

	public Player(Vector2D location, AttributeSet basicAttributes, Race race, Profession profession) {
		super(location, basicAttributes, race);
		this.profession = profession;
		this.velocity = Vector2D.ZERO;
		this.profession.init(this);
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
		return true;
	}

	@Override
	public void act(Level level) {
		boolean success;
		success = level.tryMoveBy(this, velocity);
		if (!success) {
			success = level.tryMoveBy(this, new Vector2D(velocity.getX(), 0));
			if (!success) {
				level.tryMoveBy(this, new Vector2D(0, velocity.getY()));
			}
		}
	}

	@Override
	public void onDeath(Level level) {
	}

	public Vector2D getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2D velocity) {
		this.velocity = velocity;
	}

}