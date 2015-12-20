package rpg.element.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rpg.ability.AbilityHandler;
import rpg.element.Element;
import rpg.item.Inventory;
import rpg.item.Item;
import rpg.logic.Level;
import rpg.physics.Vector2D;
import rpg.ui.Rectangle;

public abstract class Entity extends Element {

	private Vector2D direction;
	private AttributeSet basicAttributes;
	private Race race;
	private Map<String, Bar> bars;
	private boolean interacting;
	private AbilityHandler abilityHandler;
	private Inventory inventory;

	public Entity(Vector2D location, AttributeSet basicAttributes, Race race) {
		super(location);
		this.basicAttributes = basicAttributes;
		this.race = race;
		this.direction = Vector2D.NORTH;
		this.bars = new HashMap<>();
		abilityHandler = new AbilityHandler(this);
		bars.put("health", new Bar(getContinuous("maxHealth")));
		inventory = new Inventory();
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

	public double getContinuous(String name) {
		return basicAttributes.getContinuous(name) + race.getAttributeSet().getContinuous(name);
	}

	public int getDiscrete(String name) {
		return basicAttributes.getDiscrete(name) + race.getAttributeSet().getDiscrete(name);
	}

	public boolean getBoolean(String name) {
		return basicAttributes.getBoolean(name) || race.getAttributeSet().getBoolean(name);
	}

	@Override
	public void update(Level level, double dt) {
		abilityHandler.update(level, dt);
		if (isAlive()) {
			act(level, dt);
		} else {
			onDeath(level);
		}
	}

	public abstract void act(Level level, double dt);

	public void addBarValue(String name, double dvalue) {
		bars.get(name).addValue(dvalue);
	}

	public void removeBarValue(String name, double dvalue) {
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
		addBarValue(name, -value);
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
				Color[] colors = Bar.getColors(key);
				g.setColor(colors[0]);
				g.fillRect((int) rect.getX(), (int) rect.getHeight() + 8 * counter - 4,
						(int) (rect.getWidth() * percentage), 4);
				g.setColor(colors[1]);
				g.fillRect((int) (rect.getWidth() * percentage) + (int) (rect.getX()),
						(int) rect.getHeight() + 8 * counter - 4,
						(int) rect.getWidth() - (int) (rect.getWidth() * percentage), 4);
				counter++;
			}
		}
	}

	public double getBarValue(String name) {
		return bars.get(name).getValue();
	}

	public double getBarValue(String name, double defaultValue) {
		Bar bar = bars.get(name);
		return bar != null ? bar.getValue() : defaultValue;
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

	public void pick(Item item) {
		inventory.add(item);
	}

	public abstract void onDeath(Level level);

}
