package rpg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import physics.Vector;

public abstract class Entity extends VisualElement {

	private Direction direction;
	private AttributeSet basicAttributes;
	private Race race;
	private Bar healthBar;
	private Vector velocity;

	public Entity(Vector location, AttributeSet basicAttributes, Race race) {
		super(location);
		this.basicAttributes = basicAttributes;
		this.race = race;
		this.velocity = Vector.ZERO;
		this.healthBar = new Bar(race.getDefaultHealth());
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public AttributeSet getTotalAttributeSet() {
		return basicAttributes.add(race.getAttributeSet());
	}

	@Override
	public void update(Game game) {
		if (isAlive()) {
			game.tryMoveBy(this, velocity);
		}
	}

	public Vector getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	public void addHealth(double dhealth) {
		this.healthBar.addValue(dhealth);
	}

	public void removeHealth(double dhealth) {
		this.healthBar.addValue(-Math.min(dhealth, healthBar.getValue()));
	}

	public boolean isAlive() {
		return healthBar.getValue() > 0;
	}

	@Override
	public void draw(Graphics g) {
		drawEntity(g);
		double percentage = healthBar.getValue() / healthBar.getMaximum();
		g.setColor(Color.GREEN);
		Rectangle rect = getBoundingRect();
		g.fillRect(-16, (int) rect.getHeight() + 4, (int) (32 * percentage), 4);
		g.setColor(Color.RED);
		g.fillRect((int) (32 * percentage) - 16, (int) rect.getHeight() + 4, 32 - (int) (32 * percentage), 4);
	}

	protected abstract void drawEntity(Graphics g);

}
