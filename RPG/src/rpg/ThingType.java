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

public class ThingType {

	public static final ThingType ONLY_LOCATION = new ThingType(new Attribute(Vector2D.class, "location"));
	public static final ThingType PLAYER = new ThingType(new Attribute(Vector2D.class, "location"),
			new Attribute(String.class, "race"));
	public static final ThingType FIREBALL = new ThingType(new Attribute(Vector2D.class, "location"),
			new Attribute(Vector2D.class, "direction"), new Attribute(double.class, "speed"));
	public static final ThingType FIREBALL_SPELL = new ThingType(new Attribute(double.class, "speed"));
	public static final ThingType PORTAL = new ThingType(new Attribute(Vector2D.class, "location"),
			new Attribute(Vector2D.class, "target"));
	public static final ThingType NOTHING = new ThingType();

	private static Map<Class<? extends Thing>, ThingType> map = new HashMap<>();

	static {
		register(Dragon.class, ONLY_LOCATION);
		register(Player.class, PLAYER);
		register(Block.class, ONLY_LOCATION);
		register(HealthPotion.class, ONLY_LOCATION);
		register(ManaPotion.class, ONLY_LOCATION);
		register(Door.class, ONLY_LOCATION);
		register(Fireball.class, FIREBALL);
		register(FireballSpell.class, FIREBALL_SPELL);
		register(HasteSpell.class, NOTHING);
		register(Portal.class, PORTAL);
		register(Air.class, ONLY_LOCATION);
		register(Block.class, ONLY_LOCATION);
		register(AbilityHandler.class, NOTHING);
	}

	public static void register(Class<? extends Thing> cls, ThingType type) {
		map.put(cls, type);
	}

	public static Thing getThing(Class<?> cls, Thing thing) {
		System.out.println(cls);
		Attribute[] attributes = map.get(cls).attributes;
		Class<?>[] classes = new Class<?>[attributes.length];
		Object[] values = new Object[attributes.length];
		for (int i = 0; i < classes.length; i++) {
			classes[i] = attributes[i].getType();
			values[i] = thing.get(attributes[i].getKey());
		}
		try {
			Constructor<?> constructor = cls.getConstructor(classes);
			return (Thing) constructor.newInstance(values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new RPGException("Should not reach here exception");
	}

	private Attribute[] attributes;

	private ThingType(Attribute... attributes) {
		this.attributes = attributes;
	}
}
