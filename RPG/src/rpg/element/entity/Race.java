package rpg.element.entity;

import java.util.List;

import rpg.Thing;
import rpg.exception.RPGException;

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

}
