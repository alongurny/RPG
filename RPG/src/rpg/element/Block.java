package rpg.element;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.logic.level.Level;

public class Block extends Element {

	private static Image image;
	private static final String imagePath = "img/block.png";
	public static int width, height;

	static {
		try {
			image = ImageIO.read(new File(imagePath));
			width = 32;
			height = 32;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Block(Vector2D location) {
		super(location);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, -width / 2, -height / 2, width, height, null);
	}

	@Override
	public int getIndex() {
		return 100;
	}

	@Override
	public void update(Level level, double dt) {

	}

	@Override
	public boolean isPassable(Level level, Element other) {
		return false;
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-width / 2, -height / 2, width, height);
	}

	@Override
	public void onCollision(Level level, Element other) {

	}

}
