package rpg.element;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BooleanSupplier;

import rpg.ability.Ability;
import rpg.element.entity.Attribute;
import rpg.element.entity.Effect;
import rpg.element.entity.Profession;
import rpg.element.entity.Race;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.ScaleDrawer;
import rpg.graphics.Translate;
import rpg.item.Item;
import rpg.logic.level.Level;

public abstract class Entity extends Element {

	private Race race;
	private Profession profession;
	private List<Effect> effects;
	private List<Item> inventory;

	private double xp;

	private double health, mana;
	private Orientation orientation;
	private Vector2D direction;
	private Optional<Element> target;
	private Map<Attribute, Double> attributes;
	private Map<Attribute, Double> temporary;
	private Vector2D gravity = new Vector2D(0, 640);
	private Vector2D velocity = new Vector2D(0, 0);

	public Entity(Vector2D location, Race race, Profession profession) {
		super(location);
		initAttributes();
		this.race = race;
		this.profession = profession;
		this.health = getMaxHealth();
		this.mana = getMaxMana();
		this.direction = Vector2D.ZERO;
		this.orientation = Orientation.FRONT;
		this.target = Optional.empty();
		this.xp = 0;
		inventory = new ArrayList<>();
		effects = new CopyOnWriteArrayList<>();
	}

	private void initAttributes() {
		attributes = new HashMap<>();
		temporary = new HashMap<>();
		attributes.put(Attribute.STR, 10.0);
		attributes.put(Attribute.DEX, 10.0);
		attributes.put(Attribute.INT, 10.0);
		attributes.put(Attribute.CON, 10.0);
		temporary.put(Attribute.STR, 0.0);
		temporary.put(Attribute.DEX, 0.0);
		temporary.put(Attribute.INT, 0.0);
		temporary.put(Attribute.CON, 0.0);
	}

	public void addAttribute(Attribute attr, double value) {
		temporary.put(attr, temporary.get(attr) + value);
	}

	public void subtractAttribute(Attribute attr, double value) {
		temporary.put(attr, temporary.get(attr) - value);
	}

	public double getSpeed() {
		return race.getSpeed(this) + 16 * getModifier(Attribute.DEX);
	}

	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	public Vector2D getDirection() {
		return direction;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setTarget(Optional<Element> target) {
		this.target = target;
	}

	@Override
	public void update(Level level, double dt) {
		getAbilities().forEach(a -> a.update(level, dt));
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

	public boolean isRequireable(double value) {
		return mana >= value;
	}

	public void addMana(double value) {
		mana = Math.max(0, Math.min(mana + value, getMaxMana()));
	}

	public abstract boolean isFriendly(Entity other);

	protected List<Effect> getEffects() {
		return effects;
	}

	public void addHealth(double value) {
		health = Math.max(0, Math.min(health + value, getMaxHealth()));
	}

	public Optional<Element> getTarget() {
		return target;
	}

	public boolean tryRequireMana(double value) {
		if (!isRequireable(value)) {
			return false;
		}
		subtractMana(value);
		return true;
	}

	public void subtractMana(double value) {
		addMana(-value);
	}

	public void subtractHealth(double value) {
		addHealth(-value);
	}

	public boolean isAlive() {
		return health > 0;
	}

	public double getMaxMana() {
		return race.getMaxMana(this) + profession.getMaxMana(this);
	}

	public double getMaxHealth() {
		return race.getMaxHealth(this);
	}

	protected abstract Drawer getEntityDrawer();

	@Override
	public Drawer getDrawer() {
		Translate t = new Translate(0, (int) (getRelativeRect().getHeight() / 2));
		Translate s = new Translate(0, 8);
		return getEntityDrawer().andThen(t).andThen(s)
				.andThen(new ScaleDrawer(health / getMaxHealth(), Color.GREEN, Color.RED, getRelativeRect().getWidth(),
						4))
				.andThen(s)
				.andThen(new ScaleDrawer(mana / getMaxMana(), Color.BLUE, Color.CYAN, getRelativeRect().getWidth(), 4))
				.andThen(t.negate().andThen(s.negate()).andThen(s.negate()));
	}

	public void pick(Item item) {
		inventory.add(item);
	}

	public List<Item> getInventory() {
		return inventory;
	}

	public Ability getAbility(int i) {
		return getAbilities().get(i);
	}

	public List<Ability> getAbilities() {
		return profession.getAbilities();
	}

	public boolean tryCast(Level level, Ability ability, Optional<Element> element) {
		if (!ability.hasCooldown() && ability.isCastable(this, element)) {
			ability.onCast(level, this, element);
			ability.setCooldown(ability.getMaxCooldown());
			return true;
		}
		return false;
	}

	public void addXP(double xp) {
		this.xp += xp;
	}

	public int getAbilityCount() {
		return getAbilities().size();
	}

	public boolean tryCast(Level level, int i, Optional<Element> element) {
		return tryCast(level, getAbility(i), element);
	}

	public void addEffect(Effect effect) {
		effects.add(effect);
	}

	public double getMana() {
		return mana;
	}

	public double getHealth() {
		return health;
	}

	public Vector2D getVelocity() {
		return direction.multiply(getSpeed());
	}

	public double getAttribute(Attribute attr) {
		return attributes.get(attr) + race.getAttribute(attr) + temporary.get(attr);
	}

	public double getModifier(Attribute attr) {
		return getAttribute(attr) - 10;
	}

	public int getRank() {
		return (int) (Math.log(1 + xp / 1000) / Math.log(Math.sqrt(10))) + 1;
	}

	public void damage(double damage, DamageType type) {
		subtractHealth(Math.max(0, damage - getResistance(type)));
	}

	public double getResistance(DamageType type) {
		return profession.getResistance(this, type);
	}

}
