package rpg.element;

import rpg.Interactive;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.DrawIcon;
import rpg.graphics.Drawer;
import rpg.logic.level.Level;

public class Portal extends Element implements Interactive {

	private static int width = 32, height = 32;
	private Drawer drawer;
	private Vector2D target;

	public static Portal[] getPair(Vector2D v1, Vector2D v2) {
		Portal p1 = new Portal(v1, v2);
		Portal p2 = new Portal(v2, v1);
		return new Portal[] { p1, p2 };
	}

	public Portal(Vector2D location, Vector2D target) {
		super(location);
		this.target = target;
		drawer = new DrawIcon("img/portal.gif", width, height);
	}

	@Override
	public void update(Level level, double dt) {

	}

	@Override
	public Drawer getDrawer() {
		return drawer;
	}

	@Override
	public void onCollision(Level level, Element other) {

	}

	@Override
	public int getIndex() {
		return 1;
	}

	@Override
	public void onInteract(Level level, Entity entity) {
		level.tryMove(entity, target.add(entity.getLocation()).subtract(getLocation()));
	}

	@Override
	public boolean isInteractable(Level level, Entity entity) {
		Rectangle entityRect = entity.getAbsoluteRect();
		Rectangle myRect = this.getAbsoluteRect();
		Rectangle intersect = Rectangle.intersect(entityRect, myRect);
		return intersect.getWidth() >= 0.6 * entityRect.getWidth()
				&& intersect.getHeight() >= 0.6 * entityRect.getHeight();
	}

	@Override
	public boolean isPassable(Level level, Element other) {
		return true;
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-width / 2, -height / 2, width, height);
	}

}
