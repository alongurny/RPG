package rpg;

import java.util.ArrayList;
import java.util.List;

import physics.Vector;
import rpg.exception.IllegalMoveException;

public class Game {

	private List<Element> elements;

	public Game() {
		elements = new ArrayList<>();
	}

	public void move(Element element, Vector target) {
		boolean success = tryMove(element, target);
		if (!success) {
			throw new IllegalMoveException();
		}
	}

	public boolean tryMoveBy(Element element, Vector displacement) {
		return tryMove(element, element.getLocation().add(displacement));
	}

	public boolean tryMove(Element element, Vector target) {
		if (isPassable(element, target)) {
			element.setLocation(target);
			return true;
		}
		return false;
	}

	private boolean isPassable(Element element, Vector target) {
		// TODO Auto-generated method stub
		return true;
	}

	public List<Element> getElements() {
		return elements;
	}

	public void addElement(Element element) {
		elements.add(element);
	}

	public void update() {
		elements.forEach(e -> e.update(this));
	}

	public List<Player> getPlayers() {
		List<Player> players = new ArrayList<>();
		for (Element e : elements) {
			if (e instanceof Player) {
				players.add((Player) e);
			}
		}
		return players;
	}

}
