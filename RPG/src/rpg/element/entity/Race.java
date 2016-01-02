package rpg.element.entity;

import java.util.List;

import rpg.Thing;
import rpg.exception.RPGException;
import rpg.geometry.Vector2D;

public class Race extends Thing {

	public static final Race HUMAN = new Race("human", read("race/human.attr"));
	public static final Race DRAGON = new Race("dragon", read("race/dragon.attr"));

	private Race(String name, List<Attribute> attributes) {
		attributes.forEach(a -> set(a.getKey(), a.getValue()));
		set("race", name);
	}

	public static Race valueOf(String str) {
		switch (str.toLowerCase()) {
		case "human":
			return HUMAN;
		case "dragon":
			return DRAGON;
		default:
			throw new RPGException("No race " + str);
		}
	}

	public void init(Entity entity) {
		for (String key : getKeys()) {
			switch (getType(key).getSimpleName()) {
			case "Double":
				entity.set(key, entity.getNumber(key, 0.0) + getNumber(key));
				break;
			case "Boolean":
				entity.set(key, entity.getBoolean(key, false) || getBoolean(key));
				break;
			case "Vector2D":
				entity.set(key, entity.getVector(key, Vector2D.ZERO).add(getVector(key)));
				break;
			case "String":
				if (!entity.hasKey(key)) {
					entity.set(key, getString(key));
				} else {
					throw new RPGException("Key conflict: " + key);
				}
				break;
			}
		}
	}

}
