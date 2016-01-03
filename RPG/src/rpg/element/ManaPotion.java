package rpg.element;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import rpg.element.entity.Entity;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Tileset;
import rpg.logic.level.Level;

public class ManaPotion extends Bonus {

	public ManaPotion(Vector2D location) {
		super(location);
	}

	private static BufferedImage image = Tileset.subimage(24, 29);
	private static int width = image.getWidth();
	private static int height = image.getHeight();

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, -width / 2, -height / 2, width, height, null);
	}

	@Override
	protected void onPick(Entity picker) {
		picker.addBarValue("mana", 20);
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-width / 2, -height / 2, width, height);
	}

	@Override
	public int getIndex() {
		return 1;
	}

	@Override
	public void update(Level level, double dt) {

	}

}
