package rpg.element;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

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
import rpg.logic.level.Game;

/**
 * This class represents an entity: something that is living and can move and
 * act. {@link Player} is a subclass of this class.
 * 
 * @author Alon
 *
 */
public abstract class Entity extends Element {

	private Race race;
	private Profession profession;
	private List<Effect> effects;
	private List<Item> inventory;
	private double xp;
	private Vector2D velocity;
	private Vector2D acceleration;
	private double health, mana;
	private Optional<Element> target;
	private Map<Attribute, Double> attributes;
	private Map<Attribute, Double> temporary;

	public Entity(Vector2D location, Race race, Profession profession) {
		super(location);
		initAttributes();
		this.race = race;
		this.profession = profession;
		this.health = getMaxHealth();
		this.mana = getMaxMana();
		this.target = Optional.empty();
		this.xp = 0;
		this.velocity = Vector2D.ZERO;
		this.acceleration = Vector2D.ZERO;
		inventory = new ArrayList<>();
		effects = new CopyOnWriteArrayList<>();
	}

	public abstract void act(Game game, double dt);

	public void addAttribute(Attribute attr, double value) {
		temporary.put(attr, temporary.get(attr) + value);
	}

	public void addEffect(Effect effect) {
		effects.add(effect);
	}

	public void addHealth(double value) {
		health = Math.max(0, Math.min(health + value, getMaxHealth()));
	}

	public void addMana(double value) {
		mana = Math.max(0, Math.min(mana + value, getMaxMana()));
	}

	public void addXP(double xp) {
		this.xp += xp;
	}

	public void damage(double damage, DamageType type) {
		subtractHealth(Math.max(0, damage - getResistance(type)));
	}

	public List<Ability> getAbilities() {
		return profession.getAbilities();
	}

	public Ability getAbility(int i) {
		return getAbilities().get(i);
	}

	public int getAbilityCount() {
		return getAbilities().size();
	}

	public double getAttribute(Attribute attr) {
		return attributes.get(attr) + race.getAttribute(attr) + temporary.get(attr);
	}

	public Vector2D getDirection() {
		return velocity.getUnitalVector();
	}

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

	protected List<Effect> getEffects() {
		return effects;
	}

	protected abstract Drawer getEntityDrawer();

	public double getHealth() {
		return health;
	}

	public List<Item> getInventory() {
		return inventory;
	}

	public double getMana() {
		return mana;
	}

	public double getMaxHealth() {
		return race.getMaxHealth(this);
	}

	public double getMaxMana() {
		return race.getMaxMana(this) + profession.getMaxMana(this);
	}

	public double getModifier(Attribute attr) {
		return getAttribute(attr) - 10;
	}

	public int getRank() {
		return (int) (Math.log(1 + xp / 1000) / Math.log(Math.sqrt(10))) + 1;
	}

	public double getResistance(DamageType type) {
		return profession.getResistance(this, type);
	}

	public double getSpeed() {
		return race.getSpeed(this) + 16 * getModifier(Attribute.DEX);
	}

	public Optional<Element> getTarget() {
		return target;
	}

	public Vector2D getVelocity() {
		return velocity;
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

	public boolean isAlive() {
		return health > 0;
	}

	public abstract boolean isFriendly(Entity other);

	public boolean isRequireable(double value) {
		return mana >= value;
	}

	private void move(Game game, double dt) {
		boolean xMoved = game.tryMoveBy(this, new Vector2D(velocity.getX() * dt, 0));
		boolean yMoved = game.tryMoveBy(this, new Vector2D(0, velocity.getY() * dt));
		Vector2D possibleV = velocity.add(acceleration.multiply(dt));
		velocity = new Vector2D(xMoved ? possibleV.getX() : 0, yMoved ? possibleV.getY() : 0);
	}

	public void pick(Item item) {
		inventory.add(item);
	}

	public void setAcceleration(Vector2D acceleration) {
		this.acceleration = acceleration;
	}

	public void setTarget(Optional<Element> target) {
		this.target = target;
	}

	public void setVelocity(Vector2D velocity) {
		this.velocity = velocity;
	}

	public void subtractAttribute(Attribute attr, double value) {
		temporary.put(attr, temporary.get(attr) - value);
	}

	public void subtractHealth(double value) {
		addHealth(-value);
	}

	public void subtractMana(double value) {
		addMana(-value);
	}

	public boolean tryCast(Game game, Ability ability) {
		if (ability.canCast(this)) {
			ability.cast(game, this);
			return true;
		}
		return false;
	}

	public boolean tryCast(Game game, int index) {
		List<Ability> abilities = getAbilities();
		return 0 <= index && index < abilities.size() && tryCast(game, abilities.get(index));
	}

	public boolean tryRequireMana(double value) {
		if (!isRequireable(value)) {
			return false;
		}
		subtractMana(value);
		return true;
	}

	@Override
	public void update(Game game, double dt) {
		getAbilities().forEach(a -> a.update(game, dt));
		effects.forEach(e -> e.update(game, dt));
		effects.removeIf(e -> {
			if (!e.isAffecting()) {
				e.onEnd();
				return true;
			}
			return false;
		});
		move(game, dt);
		act(game, dt);
	}

}
