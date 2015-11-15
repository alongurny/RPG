package rpg;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import physics.Vector;

public class Player extends Entity {

	private static Image playerImage;
	private static String PLAYER_IMAGE_LOCATION = "img/player.png";
	private static int width, height;

	static {
		try {
			playerImage = ImageIO.read(new File(PLAYER_IMAGE_LOCATION));
			width = playerImage.getWidth(null);
			height = playerImage.getHeight(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Player(Vector location, AttributeSet basicAttributes, Race race) {
		super(location, basicAttributes, race);
	}

	@Override
	public void drawEntity(Graphics g) {
		g.drawImage(playerImage, -width / 2, -height / 2, null);
	}

	@Override
	public void onInteraction(Game game, Element other) {
	}

	@Override
	public Rectangle getBoundingRect() {
		return new Rectangle(-width / 2, -height / 2, width, height);
	}

}
