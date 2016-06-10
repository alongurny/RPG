package rpg.graphics;

import java.awt.Graphics2D;
import java.awt.Image;

import external.ImageResource;

public class DrawIcon extends Drawer {

	private Image image;
	private String path;
	private int width, height;

	public DrawIcon(String path, int width, int height) {
		this.width = width;
		this.height = height;
		this.path = path;
		this.image = ImageResource.get(path).getImage();
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image, -width / 2, -height / 2, width, height, null);
	}

	@Override
	public String represent() {
		return String.format("%s %s:java.lang.String %d:int %d:int", getClass().getName(), path, width, height);
	}

}
