package rpg.element.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BooleanSupplier;

import rpg.Cost;
import rpg.Requirement;
import rpg.ability.Ability;
import rpg.element.Element;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.item.Inventory;
import rpg.item.Item;
import rpg.logic.level.Level;

public abstract class Entity extends Element {

	private Race race;
	private Inventory inventory;
	private List<Ability> abilities;
	private List<Effect> effects;
	private Element target;

	public Entity(Vector2D location, Race race) {
		super(location);
		set("race", race.get("race"));
		set("direction", Vector2D.NORTH);
		this.race = race;
		setLimited("health", getTotalNumber("maxHealth"));
		setLimited("mana", getTotalNumber("maxMana"));
		inventory = new Inventory();
		abilities = new CopyOnWriteArrayList<>();
		effects = new CopyOnWriteArrayList<>();
	}

	@Override
	public void update(Level level, double dt) {
		abilities.forEach(a -> a.update(level, dt));
		effects.forEach(e -> e.update(level, dt));
		effects.removeIf(e -> new BooleanSupplier() {
			public boolean getAsBoolean() {
				if (!e.isAffecting()) {
					e.onEnd();
					return true;
				}
				return false;
			}
		}.getAsBoolean());
		act(level, dt);
	}

	public abstract void act(Level level, double dt);

	public boolean isRequireable(String key, double value) {
		return getDouble(key) >= value;
	}

	public boolean tryRequire(String key, double value) {
		if (!isRequireable(key, value)) {
			return false;
		}
		set(key, getDouble(key) - value);
		return true;
	}

	public boolean isAlive() {
		return getDouble("health") > 0;
	}

	public double getTotalNumber(String key) {
		return super.getDouble(key, 0.0) + race.getDouble(key, 0.0);
	}

	public Vector2D getTotalVector(String key) {
		return super.getVector(key, Vector2D.ZERO).add(race.getVector(key, Vector2D.ZERO));
	}

	private boolean getTotalBooleanFromEffects(String key) {
		for (Effect e : effects) {
			if (e.getBoolean(key, false)) {
				return true;
			}
		}
		return false;
	}

	public boolean getTotalBoolean(String key) {
		return super.getBoolean(key, false) || race.getBoolean(key, false) || getTotalBooleanFromEffects(key);
	}

	@Override
	public void draw(Graphics g) {
		drawEntity(g);
		Rectangle rect = getRelativeRect();
		int counter = 0;
		List<String> keys = new ArrayList<>(Bar.getBound());
		keys.sort(String.CASE_INSENSITIVE_ORDER);
		for (String key : keys) {
			if (Bar.isBound(key)) {
				double percentage = getDouble(key) / getMaximum(key);
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

	protected abstract void drawEntity(Graphics g);

	public void pick(Item item) {
		inventory.add(item);
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void addAbility(Ability ability) {
		abilities.add(ability);
	}

	public Ability getAbility(int i) {
		return abilities.get(i);
	}

	public List<Ability> getAbilities() {
		return new ArrayList<>(abilities);
	}

	public boolean makeCastable(Ability ability) {
		if (!ability.hasCooldown()) {
			for (Requirement r : ability.getRequirements()) {
				if (!r.isRequireable(this)) {
					return false;
				}
			}
			for (Cost c : ability.getCosts()) {
				c.cost(this);
			}
			return true;
		}
		return false;
	}

	public boolean isCastable(Ability ability) {
		if (!ability.hasCooldown()) {
			for (Requirement r : ability.getRequirements()) {
				if (!r.isRequireable(this)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public boolean tryCast(Level level, Ability ability) {
		if (makeCastable(ability)) {
			ability.onCast(level, this);
			ability.setCooldown(ability.getDouble("maxCooldown"));
			return true;
		}
		return false;
	}

	public int getAbilityCount() {
		return abilities.size();
	}

	public boolean tryCast(Level level, int i) {
		return tryCast(level, getAbility(i));
	}

	public void addEffect(Effect effect) {
		effects.add(effect);
	}

	public boolean hasTarget() {
		return target != null;
	}

	public Element getTarget() {
		return target;
	}

	public void setTarget(Element target) {
		this.target = target;
	}

}
