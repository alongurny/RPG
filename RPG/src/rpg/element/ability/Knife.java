package rpg.element.ability;

import rpg.ability.damage.DamageType;
import rpg.element.Depth;
import rpg.element.Element;
import rpg.element.entity.Entity;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.logic.level.Game;

public class Knife extends Element {

	private double angle;
	private Drawer drawer;
	private boolean attacking;

	public Knife(Vector2D location) {
		super(location);
	}

	@Override
	public Drawer getDrawer() {
		return drawer;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	@Override
	public boolean isPassable(Game game, Element other) {
		return true;
	}

	@Override
	public void onCollision(Game game, Element other) {
		if (attacking && other instanceof Entity) {
			Entity entity = (Entity) other;
			entity.damage(6, DamageType.PHYSICAL);
		}
	}

	@Override
	public void update(Game game, double dt) {
		
	}

	@Override
	public Depth getDepth() {
		return Depth.MEDIUM;
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-4, -8, 8, 16);
	}

}
