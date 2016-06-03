package rpg.graphics;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;

import rpg.ImageResource;

public class DrawPaint extends Drawer {

	private TexturePaint paint;
	private String path;
	private int width, height;

	public DrawPaint(String path, int width, int height) {
		this.width = width;
		this.height = height;
		this.path = path;
		Image image = ImageResource.get(path).getImage();
		BufferedImage bi = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		g.drawImage(image, 0, 0, bi.getWidth(), bi.getHeight(), null);
		g.dispose();
		this.paint = new TexturePaint(bi, new Rectangle(0, 0, bi.getWidth(), bi.getHeight()));
	}

	@Override
	public void draw(Graphics2D g) {
		Paint prev = g.getPaint();
		g.setPaint(paint);
		g.fillRect(0, 0, width, height);
		g.setPaint(prev);
	}

	@Override
	public String represent() {
		return String.format("%s %s:java.lang.String %d:int %d:int", getClass().getName(), path, width, height);
	}

}
