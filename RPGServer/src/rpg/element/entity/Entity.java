package rpg.element.entity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import rpg.ability.Ability;
import rpg.ability.damage.DamageType;
import rpg.element.Element;
import rpg.element.entity.profession.Profession;
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

	private Profession profession;
	private List<String> effects;
	private List<Item> inventory;
	private double xp;
	private Vector2D velocity;
	private Vector2D acceleration;
	private double health, mana;
	private Optional<Element> target;
	private Map<Attribute, Double> attributes;
	private Map<Attribute, Double> temporary;

	/**
	 * Constructs a new entity at the given location with the given profession
	 * 
	 * @param location
	 *            the initial location
	 * @param profession
	 *            the profession
	 */
	public Entity(Vector2D location, Profession profession) {
		super(location);
		initAttributes();
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

	/**
	 * Returns this entity's experience points
	 * 
	 * @return this entity's experience points
	 */
	public double getXP() {
		return xp;
	}

	/**
	 * Returns the profession of this entity
	 * 
	 * @return the profession of this entity
	 */
	public Profession getProfession() {
		return profession;
	}

	/**
	 * Performs an action. This action is left to subclasses to implement.
	 * 
	 * @param game
	 *            the game
	 * @param dt
	 *            the time passed since the last update
	 */
	protected abstract void act(Game game, double dt);

	public void addAttribute(Attribute attr, double value) {
		temporary.put(attr, temporary.get(attr) + value);
	}

	public void addEffect(String effect) {
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

	/**
	 * Returns the acceleration of this entity
	 * 
	 * @return the acceleration of this entity
	 */
	public Vector2D getAcceleration() {
		return acceleration;
	}

	public double getAttribute(Attribute attr) {
		return attributes.get(attr) + profession.getAttribute(attr) + temporary.get(attr);
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

	public double getHealth() {
		return health;
	}

	/**
	 * Returns a list of the items that this entity has.
	 * 
	 * @return a list of the items that this entity has
	 */
	public List<Item> getInventory() {
		return inventory;
	}

	public double getMana() {
		return mana;
	}

	public double getMaxHealth() {
		return profession.getMaxHealth(this);
	}

	public double getMaxMana() {
		return profession.getMaxMana(this);
	}

	public int getRank() {
		return (int) (Math.log(1 + xp / 1000) / Math.log(Math.sqrt(10))) + 1;
	}

	public double getResistance(DamageType type) {
		return profession.getResistance(this, type);
	}

	public double getSpeed() {
		return 10 * getAttribute(Attribute.DEX) - 36;
	}

	/**
	 * Returns the (optional) target of this entity. Targets are used by many
	 * abilities.
	 * 
	 * @return the (optional) target of this entity
	 */
	public Optional<Element> getTarget() {
		return target;
	}

	/**
	 * Returns the velocity of this entity.
	 * 
	 * @return the velocity of this entity
	 */
	public Vector2D getVelocity() {
		return velocity;
	}

	/**
	 * Returns <code>true</code> if the effect has been applied to this entity,
	 * <code>false</code> otherwise.
	 * 
	 * @param effect
	 *            an effect
	 * @return <code>true</code> if the effect has been applied to this entity,
	 *         <code>false</code> otherwise
	 */
	public boolean hasEffect(String effect) {
		return effects.contains(effect);
	}

	/**
	 * Returns <code>true</code> if there is more than 0 health and
	 * <code>false</code> otherwise.
	 * 
	 * @return <code>true</code> if there is more than 0 health and
	 *         <code>false</code> otherwise
	 */
	public boolean isAlive() {
		return health > 0;
	}

	/**
	 * Returns <code>true</code> if this entity is friendly to the other entity,
	 * and <code>false</code> otherwise. The main classes {@link Game},
	 * <code>Entity</code>, etc. provide no use for this method. It can be used
	 * by abilities for example.
	 * 
	 * @param other
	 *            the other entity
	 * @return <code>true</code> if this entity is friendly to the other entity,
	 *         and <code>false</code> otherwise
	 */
	public abstract boolean isFriendly(Entity other);

	/**
	 * Adds a new item to the inventory.
	 * 
	 * @param item
	 *            a new item to add
	 */
	public void pick(Item item) {
		inventory.add(item);
	}

	/**
	 * Removes an effect from this entity. Possible effects for example: flying,
	 * disabled, etc.
	 * 
	 * @param effect
	 *            the effect to remove
	 */
	public void removeEffect(String effect) {
		effects.remove(effect);
	}

	/**
	 * Sets the acceleration of this entity.
	 * 
	 * @param acceleration
	 *            the new acceleration to set
	 */
	public void setAcceleration(Vector2D acceleration) {
		this.acceleration = acceleration;
	}

	/**
	 * Sets a new target to this entity. Will be {@link Optional#empty()} if
	 * there is no target.
	 * 
	 * @param target
	 *            the target to set
	 */
	public void setTarget(Optional<Element> target) {
		this.target = target;
	}

	/**
	 * Sets the velocity of this entity
	 * 
	 * @param velocity
	 *            the new velocity to set
	 */
	public void setVelocity(Vector2D velocity) {
		this.velocity = velocity;
	}

	/**
	 * Add a temporary subtracting to an attribute.
	 * 
	 * @param attr
	 *            the attribute
	 * @param value
	 *            the subtracting
	 */
	public void subtractAttribute(Attribute attr, double value) {
		temporary.put(attr, temporary.get(attr) - value);
	}

	/**
	 * Subtracts a certain amount of health from this entity's health bar.
	 * 
	 * @param value
	 *            the value to subtract
	 */
	public void subtractHealth(double value) {
		addHealth(-value);
	}

	/**
	 * Subtracts a certain amount of mana from this entity's mana pool.
	 * 
	 * @param value
	 *            the value to subtract
	 */
	public void subtractMana(double value) {
		addMana(-value);
	}

	/**
	 * Tries to cast the ability in the given index.
	 * 
	 * @param game
	 *            the game
	 * @param ability
	 *            the ability to cast
	 * @return <code>true</code> if the casting was successful and
	 *         <code>false</code> otherwise.
	 */
	public boolean tryCast(Game game, Ability ability) {
		if (ability.canCast(this)) {
			ability.cast(game, this);
			return true;
		}
		return false;
	}

	/**
	 * Tries to cast the ability in the given index.
	 * 
	 * @param game
	 *            the game
	 * @param index
	 *            the index of the ability
	 * @return <code>true</code> if the casting was successful and
	 *         <code>false</code> otherwise.
	 */
	public boolean tryCast(Game game, int index) {
		List<Ability> abilities = getAbilities();
		return 0 <= index && index < abilities.size() && tryCast(game, abilities.get(index));
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Updates this entity's abilities, moves it and calls
	 * {@link #act(Game, double) act}.
	 * </p>
	 */
	@Override
	public void update(Game game, double dt) {
		getAbilities().forEach(a -> a.update(game, dt));
		if (isAlive()) {
			move(game, dt);
			act(game, dt);
		}
	}

	protected abstract Drawer getEntityDrawer();

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

	private void move(Game game, double dt) {
		boolean xMoved = game.tryMoveBy(this, new Vector2D(velocity.getX() * dt, 0));
		boolean yMoved = game.tryMoveBy(this, new Vector2D(0, velocity.getY() * dt));
		Vector2D possibleV = velocity.add(acceleration.multiply(dt));
		velocity = new Vector2D(xMoved ? possibleV.getX() : 0, yMoved ? possibleV.getY() : 0);
	}

}
