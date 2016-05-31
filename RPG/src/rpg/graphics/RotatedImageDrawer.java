package rpg.graphics;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import rpg.ImageResource;
import rpg.graphics.draw.Drawer;

public class RotatedImageDrawer extends Drawer {

	private BufferedImage image;
	private int width, height;
	private double angle;
	private String path;

	public RotatedImageDrawer(String path, int width, int height, double angle) {
		this.width = width;
		this.height = height;
		this.angle = angle;
		this.path = path;
		this.image = ImageResource.get(path).getImage();
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, -width / 2, -height / 2, width, height, null);
		AffineTransform at = new AffineTransform();
		at.rotate(angle, image.getWidth(null) / 2, image.getHeight(null) / 2);
		AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		g.drawImage(op.filter(image, null), -width / 2, -height / 2, width, height, null);
	}

	@Override
	public String represent() {
		return String.format("RotatedImageDrawer %s:java.lang.String %d:int %d:int %f:double", path, width, height,
				angle);
	}

}
