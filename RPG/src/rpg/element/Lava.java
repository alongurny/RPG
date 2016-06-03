package rpg.element;

import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.Sprite;
import rpg.graphics.TileDrawer;
import rpg.logic.level.Level;

public class Lava extends Element {

	private Sprite sprite = TileDrawer.sprite(0, 13, 49, 52);
	private double count = 0;
	private double dt;

	public Lava(Vector2D location) {
		super(location);
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-16, -16, 32, 32);
	}

	@Override
	public int getIndex() {
		return 1;
	}

	@Override
	public void onCollision(Level level, Element other) {
		if (other instanceof Entity) {
			Entity entity = (Entity) other;
			entity.damage(2.5 * dt, DamageType.FIRE);
		}
	}

	@Override
	public boolean isPassable(Level level, Element other) {
		return true;
	}

	@Override
	public Drawer getDrawer() {
		return sprite;
	}

	@Override
	public void update(Level level, double dt) {
		this.dt = dt;
		count += dt;
		if (count >= 0.12) {
			sprite.step();
			count = 0;
		}

	}

}
