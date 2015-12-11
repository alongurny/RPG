package rpg.element;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import rpg.level.Level;

public class Block extends StaticElement {

	private static Image image;
	private static final String imagePath = "img/block.png";
	public static int width, height;

	static {
		try {
			image = ImageIO.read(new File(imagePath));
			width = image.getWidth(null);
			height = image.getHeight(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Block(int y, int x) {
		super(y, x);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, 0, 0, 32, 32, null);
	}

	@Override
	public int getIndex() {
		return 100;
	}

	@Override
	public void update(Level level) {

	}

	@Override
	public boolean isPassable(Level level, Element other) {
		return false;
	}

}
