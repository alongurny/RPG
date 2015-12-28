package rpg.element.entity;

import java.util.List;

import rpg.Thing;
import rpg.exception.RPGException;
import rpg.physics.Vector2D;

public class Race extends Thing {

	public static final Race HUMAN = new Race(read("race/human.attr"));
	public static final Race DRAGON = new Race(read("race/dragon.attr"));

	public Race(List<Attribute> attributes) {
		attributes.forEach(a -> set(a.getKey(), a.getValue()));
	}

	public static Race valueOf(String str) {
		switch (str) {
		case "HUMAN":
			return HUMAN;
		case "DRAGON":
			return DRAGON;
		default:
			throw new RPGException("No race " + str);
		}
	}

	public void init(Entity entity) {
		for (String key : getKeys()) {
			switch (getType(key).getSimpleName()) {
			case "Double":
				entity.set(key, entity.getContinuous(key, 0.0) + getContinuous(key));
				break;
			case "Integer":
				entity.set(key, entity.getDiscrete(key, 0) + getDiscrete(key));
				break;
			case "Boolean":
				entity.set(key, entity.getBoolean(key, false) || getBoolean(key));
				break;
			case "Vector2D":
				entity.set(key, entity.getVector(key, Vector2D.ZERO).add(getVector(key)));
				break;
			case "String":
				if (!hasKey(key)) {
					entity.set(key, getString(key));
				} else {
					throw new RPGException("Key conflict: " + key);
				}
				break;
			}
		}
	}

}
