package rpg.element;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Sprite;
import rpg.graphics.Tileset;
import rpg.logic.level.Level;

public class IceBlock extends Element {

	private static final Sprite sprite = Sprite.get(Tileset.get(0), 16, 27);
	private static final BufferedImage image = sprite.get(0);

	public IceBlock(Vector2D location, double size) {
		super(location);
		set("size", size);
	}

	@Override
	public void draw(Graphics g) {
		int size = (int) getDouble("size");
		((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
		g.drawImage(image, -size / 2, -size / 2, size, size, null);
		((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}

	@Override
	public int getIndex() {
		return 100;
	}

	@Override
	public void update(Level level, double dt) {

	}

	@Override
	public boolean isPassable(Level level, Element other) {
		return false;
	}

	@Override
	public Rectangle getRelativeRect() {
		double size = getDouble("size");
		return new Rectangle(-size / 2, -size / 2, size, size);
	}

	@Override
	public void onCollision(Level level, Element other) {
		if (other instanceof Fireball) {
			level.removeDynamicElement(this);
		}
	}

}
