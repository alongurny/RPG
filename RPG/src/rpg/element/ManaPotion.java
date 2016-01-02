package rpg.element;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import rpg.element.entity.Entity;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.logic.level.Level;

public class ManaPotion extends Bonus {

	public ManaPotion(Vector2D location) {
		super(location);
	}

	private static BufferedImage image;
	private static final String imagePath = "img/manaPotion.gif";
	private static int width, height;

	static {
		try {
			image = ImageIO.read(new File(imagePath));
			width = 32;
			height = 32;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, -width / 2, -height / 2, width, height, null);
	}

	@Override
	protected void onPick(Entity picker) {
		picker.addBarValue("mana", 20);
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-width / 2, -height / 2, width, height);
	}

	@Override
	public int getIndex() {
		return 1;
	}

	@Override
	public void update(Level level, double dt) {

	}

}
