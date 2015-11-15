package rpg;

import java.awt.Graphics;
import java.awt.Rectangle;

import physics.Vector;

public abstract class VisualElement extends Element {

	public VisualElement(Vector location) {
		super(location);
	}

	public abstract void draw(Graphics g);

	public abstract Rectangle getBoundingRect();
}
