package rpg.graphics.draw;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class IconDrawer extends Drawer {

	private Image image;
	private int width, height;

	public IconDrawer(String path, int width, int height) {
		set("path", path);
		set("width", width);
		set("height", height);
		this.width = width;
		this.height = height;
		try {
			this.image = ImageIO.read(new File(path).toURI().toURL());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, -width / 2, -height / 2, width, height, null);
	}

}
