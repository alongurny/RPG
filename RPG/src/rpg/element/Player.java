package rpg.element;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import rpg.AttributeSet;
import rpg.Bar;
import rpg.Race;
import rpg.level.Level;
import rpg.physics.Vector2D;

public class Player extends Entity {

	private static Image playerImage;
	private static final String PLAYER_IMAGE_LOCATION = "img/player.png";
	private static int width, height;
	private Bar mana;

	static {
		try {
			playerImage = ImageIO.read(new File(PLAYER_IMAGE_LOCATION));
			width = playerImage.getWidth(null);
			height = playerImage.getHeight(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Player(Vector2D location, AttributeSet basicAttributes, Race race) {
		super(location, basicAttributes, race);
		mana = new Bar(getDefaultMana());
		putBar("mana", mana);
	}

	private double getDefaultMana() {
		return Math.max(0, getTotalAttributeSet().getIntelligenceModifier() * 4);
	}

	@Override
	public void drawEntity(Graphics g) {
		g.drawImage(playerImage, -width / 2, -height / 2, null);
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

	}

	@Override
	public void onDeath(Level level) {

	}

}