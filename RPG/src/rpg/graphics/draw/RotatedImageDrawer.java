package rpg.graphics.draw;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class RotatedImageDrawer extends Drawer {

	private static final Map<String, BufferedImage> cache = new HashMap<>();

	private BufferedImage image;
	private int width, height;
	private double angle;

	public RotatedImageDrawer(String path, int width, int height, double angle) {
		set("path", path);
		set("width", width);
		set("height", height);
		set("angle", angle);
		this.width = width;
		this.height = height;
		this.angle = angle;
		if (cache.containsKey(path)) {
			this.image = cache.get(path);
		} else {
			try {
				this.image = ImageIO.read(new File(path).toURI().toURL());
				cache.put(path, image);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, -width / 2, -height / 2, width, height, null);
		AffineTransform at = new AffineTransform();
		at.rotate(angle, image.getWidth(null) / 2, image.getHeight(null) / 2);
		AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		g.drawImage(op.filter(image, null), -width / 2, -height / 2, width, height, null);
	}

}
