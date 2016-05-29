package rpg.element;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BooleanSupplier;

import rpg.ability.Ability;
import rpg.element.entity.Bar;
import rpg.element.entity.Effect;
import rpg.element.entity.Race;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.graphics.draw.Drawable;
import rpg.graphics.draw.Drawer;
import rpg.item.Item;
import rpg.logic.level.Level;

public abstract class Entity extends Element {

	private Race race;
	private List<Ability> abilities;
	private List<Effect> effects;
	private List<Item> inventory;

	public Entity(Vector2D location, Race race) {
		super(location);
		set("race", race.get("race"));
		set("direction", Vector2D.NORTH);
		this.race = race;
		set("targetID", -1);
		setLimited("health", getTotalNumber("maxHealth"));
		setLimited("mana", getTotalNumber("maxMana"));
		inventory = new ArrayList<Item>();
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

	protected abstract Drawable getEntityDrawer();

	@Override
	public Drawer getDrawer() {
		return new Drawer() {

			@Override
			public void draw(Graphics g) {
				getEntityDrawer().draw(g);
				Rectangle rect = getRelativeRect();
				int counter = 0;
				List<String> keys = new ArrayList<>(Bar.getBound());
				keys.sort(String.CASE_INSENSITIVE_ORDER);
				for (String key : keys) {
					if (Bar.isBound(key)) {
						double percentage = Entity.this.getDouble(key) / Entity.this.getMaximum(key);
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
		};
	}

	public void pick(Item item) {
		inventory.add(item);
	}

	public List<Item> getInventory() {
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

	public boolean tryCast(Level level, Ability ability, Element... elements) {
		if (!ability.hasCooldown() && ability.isCastable(this, elements)) {
			ability.onCast(level, this, elements);
			ability.setCooldown(ability.getDouble("maxCooldown"));
			return true;
		}
		return false;
	}

	public int getAbilityCount() {
		return abilities.size();
	}

	public boolean tryCast(Level level, int i, Element... elements) {
		return tryCast(level, getAbility(i), elements);
	}

	public void addEffect(Effect effect) {
		effects.add(effect);
	}

}
