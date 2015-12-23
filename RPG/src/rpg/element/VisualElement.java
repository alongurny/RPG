package rpg.element;

import java.awt.Graphics;
import java.awt.Image;

import rpg.physics.Vector2D;
import rpg.ui.Rectangle;

public abstract class VisualElement extends Element {

	public VisualElement(Vector2D location) {
		super(location);
	}

	@Override
	public void draw(Graphics g) {
		Image image = getImage();
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		g.drawImage(image, -width / 2, -height / 2, width, height, null);
	}

	@Override
	public Rectangle getRelativeRect() {
		Image image = getImage();
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		return new Rectangle(-width / 2, -height / 2, width, height);
	}

	public abstract Image getImage();

}
