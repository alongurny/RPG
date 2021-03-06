package rpg.graphics;

import java.awt.Graphics2D;
import java.awt.Image;

import external.ImageResource;
import rpg.data.Lazy;

public class DrawIcon extends Drawer {

	private Lazy<Image> image;
	private String path;
	private int width, height;

	public DrawIcon(String path, int width, int height) {
		this.width = width;
		this.height = height;
		this.path = path;
		this.image = new Lazy<>(() -> ImageResource.get(path).getImage());
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image.get(), -width / 2, -height / 2, width, height, null);
	}

	@Override
	public String represent() {
		return String.format("%s %s:java.lang.String %d:int %d:int", getClass().getName(), path, width, height);
	}

}
