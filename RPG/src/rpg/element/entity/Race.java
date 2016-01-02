package rpg.element.entity;

import java.util.List;

import rpg.Thing;
import rpg.exception.RPGException;

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

}
