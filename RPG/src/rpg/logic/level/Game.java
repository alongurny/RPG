package rpg.logic.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

import rpg.Interactive;
import rpg.element.Element;
import rpg.element.Entity;
import rpg.element.Player;
import rpg.element.entity.Profession;
import rpg.element.entity.Race;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.logic.Grid;
import rpg.logic.Timer;
import tcp.TcpClient;

public class Game {

	private List<Element> elements;
	private List<Element> toRemove;
	private List<Element> toAdd;
	private Grid grid;
	private boolean finished;
	private Game nextLevel;
	private Timer timer;
	private List<Vector2D> initialLocations;
	private Set<Integer> boundIndices;
	private Map<Integer, Player> players;

	public Game(int rows, int cols) {
		elements = new CopyOnWriteArrayList<>();
		toRemove = new CopyOnWriteArrayList<>();
		toAdd = new CopyOnWriteArrayList<>();
		grid = new Grid(rows, cols);
		timer = new Timer();
		initialLocations = new CopyOnWriteArrayList<>();
		players = new ConcurrentHashMap<>();
		boundIndices = new ConcurrentSkipListSet<>();
	}

	public void addDynamicElement(Element element) {
		toAdd.add(element);
	}

	public void addInitialLocation(Vector2D location) {
		initialLocations.add(location);
	}

	public void addPlayer(TcpClient client, Race race, Profession profession) {
		if (players.size() < initialLocations.size()) {
			int index = getFreeIndex();
			Player player = new Player(client, initialLocations.get(index), race, profession);
			boundIndices.add(index);
			players.put(index, player);
			elements.add(player);
		}
	}

	public void addStaticElement(Element e) {
		grid.add(e);
	}

	public void addTimer(double time, Runnable run) {
		timer.add(time, run);
	}

	public boolean checkCollision(Element e, Element f) {
		return e.getAbsoluteRect().intersects(f.getAbsoluteRect());
	}

	public void finish() {
		finished = true;
	}

	public List<Element> getDynamicElements() {
		return new ArrayList<>(elements);
	}

	public List<Element> getElements(Vector2D target) {
		List<Element> elements = new ArrayList<>(this.elements);
		elements.addAll(getStaticElements());
		elements.sort((a, b) -> a.getIndex() - b.getIndex());
		elements.removeIf(e -> !e.getAbsoluteRect().contains(target));
		return elements;
	}

	private int getFreeIndex() {
		int i = 0;
		while (boundIndices.contains(i)) {
			i++;
		}
		return i;
	}

	public Grid getGrid() {
		return grid;
	}

	public Game getNextLevel() {
		return nextLevel;
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

	public List<Element> getObstaclesFromMove(Element element, Vector2D target) {
		List<Element> obstacles = getObstacles(element, target);
		if (obstacles.isEmpty()) {
			element.setLocation(target);
		}
		return obstacles;
	}

	public List<Element> getObstaclesFromMoveBy(Element element, Vector2D displacement) {
		return getObstaclesFromMove(element, element.getLocation().add(displacement));
	}

	public Optional<Player> getPlayer(TcpClient client) {
		return players.values().stream().filter(p -> p.getClient() == client).findFirst();
	}

	public int getPlayersCount() {
		return boundIndices.size();
	}

	public List<Element> getStaticElements() {
		return grid.getElements();
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

	public void interact(Entity entity, Interactive interactive) {
		interactive.onInteract(this, entity);
	}

	public boolean isFinished() {
		return finished;
	}

	public boolean isReady() {
		return boundIndices.size() >= 1;
	}

	public void moveByAndPush(Element element, Vector2D displacement) {
		List<Element> obstacles = getObstacles(element, element.getLocation().add(displacement));
		for (Element e : obstacles) {
			tryMoveBy(e, displacement);
		}
		element.setLocation(element.getLocation().add(displacement));
	}

	public void onClick(Player player, Vector2D target) {
		player.setTarget(getElements(target).stream().findFirst());
	}

	public void removeDynamicElement(Element element) {
		toRemove.add(element);
	}

	public void removePlayer(TcpClient c) {
		Entry<Integer, Player> entry = players.entrySet().stream().filter(e -> e.getValue().getClient() == c)
				.findFirst().get();
		removeDynamicElement(entry.getValue());
		players.remove(entry.getKey());
		boundIndices.remove(entry.getKey());
	}

	public void setNextLevel(Game nextLevel) {
		this.nextLevel = nextLevel;
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

	public boolean tryMove(Element element, Vector2D target) {
		return getObstaclesFromMove(element, target).isEmpty();
	}

	public boolean tryMoveBy(Element element, Vector2D displacement) {
		return getObstaclesFromMoveBy(element, displacement).isEmpty();
	}

	public void update(double dt) {
		elements.forEach(e -> e.update(this, dt));
		handleCollisions();
		timer.update(this, dt);
		elements.addAll(toAdd);
		elements.removeAll(toRemove);
		players.values().forEach(p -> {
			if (p.getTarget().isPresent() && toRemove.contains(p.getTarget().get())) {
				p.setTarget(Optional.empty());
			}
		});
		if (!toAdd.isEmpty() || !toRemove.isEmpty()) {
			elements.sort((a, b) -> a.getIndex() - b.getIndex());
		}
		toAdd.clear();
		toRemove.clear();
	}
}
