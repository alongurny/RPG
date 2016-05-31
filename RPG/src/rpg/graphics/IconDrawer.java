package rpg.graphics;

import java.awt.Graphics;
import java.awt.Image;

import rpg.ImageResource;
import rpg.graphics.draw.Drawer;

public class IconDrawer extends Drawer {

	private Image image;
	private String path;
	private int width, height;

	public IconDrawer(String path, int width, int height) {
		this.width = width;
		this.height = height;
		this.path = path;
		this.image = ImageResource.get(path).getImage();
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, -width / 2, -height / 2, width, height, null);
	}

	@Override
	public String represent() {
		return String.format("IconDrawer %s:java.lang.String %d:int %d:int", path, width, height);
	}

}
