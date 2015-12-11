package rpg.element;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rpg.AbilityHandler;
import rpg.AttributeSet;
import rpg.Bar;
import rpg.Pair;
import rpg.Race;
import rpg.level.Level;
import rpg.physics.Vector2D;

public abstract class Entity extends DynamicElement {

	private Vector2D direction;
	private AttributeSet basicAttributes;
	private Race race;
	private Map<String, Bar> bars;
	private Vector2D velocity;
	private boolean interacting;
	private AbilityHandler abilityHandler;

	public Entity(Vector2D location, AttributeSet basicAttributes, Race race) {
		super(location);
		this.basicAttributes = basicAttributes;
		this.race = race;
		this.velocity = Vector2D.ZERO;
		this.direction = Vector2D.NORTH;
		this.bars = new HashMap<>();
		abilityHandler = new AbilityHandler(this);
		bars.put("health", new Bar(race.getDefaultHealth()));
	}

	public AbilityHandler getAbilityHandler() {
		return abilityHandler;
	}

	public Vector2D getDirection() {
		return direction;
	}

	public void setDirection(Vector2D direction) {
		this.direction = direction.getUnitalVector();
	}

	public AttributeSet getTotalAttributeSet() {
		return basicAttributes.add(race.getAttributeSet());
	}

	@Override
	public void update(Level level) {
		abilityHandler.update(level);
		if (isAlive()) {
			act(level);
			level.tryMoveBy(this, velocity);
		} else {
			onDeath(level);
		}
	}

	public abstract void act(Level level);

	public Vector2D getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2D velocity) {
		this.velocity = velocity;
	}

	public void addBarValue(double dvalue, String name) {
		bars.get(name).addValue(dvalue);
	}

	public void removeBarValue(double dvalue, String name) {
		bars.get(name).addValue(-Math.min(dvalue, getBarValue(name)));
	}

	public boolean isRequireable(String name, double value) {
		double current = getBarValue(name);
		return current >= value;
	}

	public boolean tryRequire(String name, double value) {
		if (!isRequireable(name, value)) {
			return false;
		}
		addBarValue(-value, name);
		return true;
	}

	public boolean isAlive() {
		return getBarValue("health") > 0;
	}

	@Override
	public void draw(Graphics g) {
		drawEntity(g);
		Rectangle rect = getRelativeRect();
		int counter = 0;
		List<String> keys = new ArrayList<>(bars.keySet());
		keys.sort(String.CASE_INSENSITIVE_ORDER);
		for (String key : keys) {
			if (Bar.isBound(key)) {
				double percentage = getBarValue(key) / getBarMaximum(key);
				Pair<Color, Color> colors = Bar.getColors(key);
				g.setColor(colors.getFirst());
				g.fillRect(rect.x, (int) rect.height + 8 * counter - 4, (int) (rect.width * percentage), 4);
				g.setColor(colors.getSecond());
				g.fillRect((int) (rect.width * percentage) + rect.x, (int) rect.height + 8 * counter - 4,
						rect.width - (int) (rect.width * percentage), 4);
				counter++;
			}
		}
	}

	public double getBarValue(String name) {
		return bars.get(name).getValue();
	}

	public double getBarMaximum(String name) {
		return bars.get(name).getMaximum();
	}

	protected abstract void drawEntity(Graphics g);

	public boolean isInteracting() {
		return interacting;
	}

	public void setInteracting(boolean interacting) {
		this.interacting = interacting;
	}

	public void putBar(String name, Bar bar) {
		bars.put(name, bar);
	}

	public abstract void onDeath(Level level);

}
