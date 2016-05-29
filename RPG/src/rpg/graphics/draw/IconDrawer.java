package rpg.graphics.draw;

import java.awt.Graphics;
import java.awt.Image;

import rpg.ImageResource;

public class IconDrawer extends Drawer {

	private Image image;
	private int width, height;

	public IconDrawer(String path, int width, int height) {
		this.width = width;
		this.height = height;
		this.image = ImageResource.get(path).getImage();
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, -width / 2, -height / 2, width, height, null);
	}

}
