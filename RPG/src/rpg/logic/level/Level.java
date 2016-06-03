package rpg.logic.level;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import rpg.Interactive;
import rpg.element.Element;
import rpg.element.Entity;
import rpg.element.Player;
import rpg.exception.RPGException;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.logic.Grid;
import rpg.logic.Timer;

public class Level {

	private List<Element> elements;

	private List<Element> toRemove;
	private List<Element> toAdd;
	private Grid grid;
	private boolean finished;
	private Level nextLevel;
	private Timer timer;
	private List<Vector2D> initialLocations;
	private List<Player> players;

	public Level(int rows, int cols) {
		elements = new CopyOnWriteArrayList<>();
		toRemove = new CopyOnWriteArrayList<>();
		toAdd = new CopyOnWriteArrayList<>();
		grid = new Grid(rows, cols);
		timer = new Timer();
		initialLocations = new CopyOnWriteArrayList<>();
		players = new CopyOnWriteArrayList<>();
	}

	public List<Element> tryMoveBy(Element element, Vector2D displacement) {
		return tryMove(element, element.getLocation().add(displacement));
	}

	public List<Element> tryMove(Element element, Vector2D target) {
		List<Element> obstacles = getObstacles(element, target);
		if (obstacles.isEmpty()) {
			element.setLocation(target);
		}
		return obstacles;
	}

	public void interact(Entity entity, Interactive interactive) {
		interactive.onInteract(this, entity);
	}

	public List<Element> getObstacles(Element element, Vector2D target) {
		Rectangle oldRect = element.getAbsoluteRect();
		double x = target.getX() - element.getLocation().getX() + oldRect.getX();
		double y = target.getY() - element.getLocation().getY() + oldRect.getY();
		Rectangle newRect = new Rectangle(x, y, oldRect.getWidth(), oldRect.getHeight());
		List<Element> obstacles = new ArrayList<>();
		for (Element e : elements) {
			if (e != element) {
				if (e.getAbsoluteRect().intersects(newRect) && !e.isPassable(this, element)) {
					obstacles.add(e);
				}
			}
		}
		for (Element e : grid.getElements()) {
			if (e != element) {
				if (e.getAbsoluteRect().intersects(newRect) && !e.isPassable(this, element)) {
					obstacles.add(e);
				}
			}
		}
		return obstacles;
	}

	public void onClick(Player player, Vector2D target) {
		player.setTarget(getElements(target).stream().findFirst());
	}

	public void addInitialLocation(Vector2D location) {
		initialLocations.add(location);
	}

	public void addPlayer(Player player) {
		if (players.size() < initialLocations.size()) {
			player.setLocation(initialLocations.get(players.size()));
			players.add(player);
			elements.add(player);
		} else {
			throw new RPGException("Cannot add player - not enough initial locations");
		}
	}

	public List<Element> getDynamicElements() {
		return new ArrayList<>(elements);
	}

	public void addDynamicElement(Element element) {
		toAdd.add(element);
	}

	public void removeDynamicElement(Element element) {
		toRemove.add(element);
	}

	public void update(double dt) {
		elements.forEach(e -> e.update(this, dt));
		handleCollisions();
		timer.update(this, dt);
		elements.addAll(toAdd);
		elements.removeAll(toRemove);
		if (!toAdd.isEmpty() || !toRemove.isEmpty()) {
			elements.sort((a, b) -> a.getIndex() - b.getIndex());
		}
		toAdd.clear();
		toRemove.clear();
	}

	public boolean isFinished() {
		return finished;
	}

	public void finish() {
		finished = true;
	}

	public void handleCollisions() {
		for (Element e : elements) {
			for (Element f : elements) {
				if (e != f) {
					if (f.getAbsoluteRect().intersects(e.getAbsoluteRect())) {
						f.onCollision(this, e);
					}
				}
			}
			for (Element s : grid.getElements()) {
				Rectangle r = s.getAbsoluteRect();
				if (r.intersects(e.getAbsoluteRect())) {
					s.onCollision(this, e);
					e.onCollision(this, s);
				}
			}
		}
	}

	public boolean checkCollision(Element e, Element f) {
		return e.getAbsoluteRect().intersects(f.getAbsoluteRect());
	}

	public boolean tryInteract(Entity entity) {
		for (Element e : elements) {
			if (e instanceof Interactive) {
				Interactive i = (Interactive) e;
				if (i.isInteractable(this, entity)) {
					i.onInteract(this, entity);
					return true;
				}
			}
		}
		for (Element e : grid.getElements()) {
			if (e instanceof Interactive) {
				Interactive i = (Interactive) e;
				if (i.isInteractable(this, entity)) {
					i.onInteract(this, entity);
					return true;
				}
			}
		}
		return false;
	}

	public void addStaticElement(Element e) {
		grid.add(e);
	}

	public Grid getGrid() {
		return grid;
	}

	public Level getNextLevel() {
		return nextLevel;
	}

	public void setNextLevel(Level nextLevel) {
		this.nextLevel = nextLevel;
	}

	public List<Element> getStaticElements() {
		return grid.getElements();
	}

	public void addTimer(double time, Runnable run) {
		timer.add(time, run);
	}

	public Player getPlayer(int index) {
		for (Element e : elements) {
			if (e instanceof Player) {
				if (index == 0) {
					return (Player) e;
				}
				index--;
			}
		}
		throw new IndexOutOfBoundsException("No player " + index);
	}

	public List<Element> getElements(Vector2D target) {
		List<Element> elements = new ArrayList<>(this.elements);
		elements.addAll(getStaticElements());
		elements.sort((a, b) -> a.getIndex() - b.getIndex());
		elements.removeIf(e -> !e.getAbsoluteRect().contains(target));
		return elements;
	}
}
