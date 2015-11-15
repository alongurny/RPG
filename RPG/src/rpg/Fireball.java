package rpg;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import physics.Vector;

public class Fireball extends VisualElement {

	public Fireball(Vector location) {
		super(location);
	}

	private static Image image;
	private static int width, height;

	static {
		try {
			image = ImageIO.read(new File("img/fireball.gif"));
			width = image.getWidth(null);
			height = image.getHeight(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, -image.getWidth(null) / 2, -image.getHeight(null) / 2, null);
	}

	@Override
	public Rectangle getBoundingRect() {
		return new Rectangle(-width / 2, -height / 2, width, height);
	}

	@Override
	public void update(Game game) {

	}

	@Override
	public void onInteraction(Game game, Element other) {
		if (other instanceof Player) {
			Player p = (Player) other;
			p.removeHealth(10);
		}
	}

}
