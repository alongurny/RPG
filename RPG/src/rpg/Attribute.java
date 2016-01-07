package rpg;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import rpg.ability.AbilityHandler;
import rpg.ability.FireballSpell;
import rpg.ability.HasteSpell;
import rpg.element.Air;
import rpg.element.Block;
import rpg.element.Door;
import rpg.element.Fireball;
import rpg.element.HealthPotion;
import rpg.element.ManaPotion;
import rpg.element.Portal;
import rpg.element.entity.Dragon;
import rpg.element.entity.Player;
import rpg.exception.RPGException;
import rpg.geometry.Vector2D;

public class Attribute {

	public static final Attribute LOCATION = new Attribute(Vector2D.class, "location");
	public static final Attribute DIRECTION = new Attribute(Vector2D.class, "direction");
	public static final Attribute SPEED = new Attribute(double.class, "speed");
	public static final Attribute TARGET = new Attribute(Vector2D.class, "target");

	public static final Attribute[] PLAYER = { new Attribute(Vector2D.class, "location"),
			new Attribute(String.class, "race") };
	private static Map<Class<? extends Thing>, Attribute[]> map = new HashMap<>();

	static {
		register(Air.class, Attribute.LOCATION);
		register(Block.class, Attribute.LOCATION);
		register(Dragon.class, Attribute.LOCATION);
		register(Player.class, PLAYER);
		register(Block.class, Attribute.LOCATION);
		register(HealthPotion.class, Attribute.LOCATION);
		register(ManaPotion.class, Attribute.LOCATION);
		register(Door.class, Attribute.LOCATION);
		register(Fireball.class, Attribute.LOCATION, Attribute.DIRECTION, Attribute.SPEED);
		register(FireballSpell.class, Attribute.SPEED);
		register(HasteSpell.class);
		register(Portal.class, Attribute.LOCATION, Attribute.TARGET);
		register(AbilityHandler.class);
	}

	public static void register(Class<? extends Thing> cls, Attribute... attributes) {
		map.put(cls, attributes);
	}

	public static Thing getThing(Class<?> cls, Thing attributeHolder) {
		Attribute[] attributes = map.get(cls);
		Class<?>[] classes = new Class<?>[attributes.length];
		Object[] values = new Object[attributes.length];
		for (int i = 0; i < classes.length; i++) {
			classes[i] = attributes[i].getType();
			values[i] = attributeHolder.get(attributes[i].getKey());
		}
		try {
			Constructor<?> constructor = cls.getConstructor(classes);
			return (Thing) constructor.newInstance(values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new RPGException("Should not reach here exception");
	}

	private String key;
	private Class<?> type;

	public Attribute(Class<?> type, String key) {
		this.key = key;
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public Class<?> getType() {
		return type;
	}

}
