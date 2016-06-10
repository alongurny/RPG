package rpg.logic.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

import network.TcpClient;
import rpg.element.Element;
import rpg.element.Interactive;
import rpg.element.entity.Entity;
import rpg.element.entity.Player;
import rpg.element.entity.profession.Profession;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.logic.Grid;
import rpg.logic.Timer;

/**
 * <code>Game</code> is the main class that runs the whole game. It consists of
 * several things:
 * <ul>
 * <li>A grid, which contains <b>static elements</b>.</li>
 * <li>Elements</li>
 * <li>A timer: used to schedule events</li>
 * </ul>
 * 
 * @author Alon
 *
 */
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

	/**
	 * Constructs a new game with a new grid.
	 * 
	 * @param rows
	 *            the number of rows of the grid.
	 * @param cols
	 *            the number of columns of the grid.
	 * @see Grid
	 */
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

	/**
	 * Add a new dynamic element. Dynamic elements are not guaranteed to be
	 * static - they can move.
	 * 
	 * @param element
	 *            the element to add
	 */
	public void addDynamicElement(Element element) {
		toAdd.add(element);
	}

	/**
	 * Add a new location in which the next player will be placed. This class
	 * tracks these initial locations, so if, for example, players A, B and C
	 * are placed in locations X, Y and Z and then B disconnects, the next
	 * player D will be placed at Y.
	 * 
	 * @param location
	 */
	public void addInitialLocation(Vector2D location) {
		initialLocations.add(location);
	}

	/**
	 * Add a new player to the game, given the client that wants to create it, a
	 * race and a profession.
	 * 
	 * @param client
	 *            the client that created this player
	 * @param race
	 *            the new player's race
	 * @param profession
	 *            the new player's profession
	 */
	public void addPlayer(TcpClient client, Profession profession) {
		if (players.size() < initialLocations.size()) {
			int index = getFreeIndex();
			Player player = new Player(client, initialLocations.get(index), profession);
			boundIndices.add(index);
			players.put(index, player);
			elements.add(player);
		}
	}

	/**
	 * Add a new static element to the game. Static elements are guaranteed not
	 * to move after being added to the game.
	 * 
	 * @param element
	 *            a static element to add
	 */
	public void addStaticElement(Element element) {
		grid.add(element);
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
		elements.removeIf(e -> !e.getAbsoluteRect().contains(target));
		return elements;
	}

	public Grid getGrid() {
		return grid;
	}

	public Game getNextLevel() {
		return nextLevel;
	}

	/**
	 * Returns the obstacles that block an element's way to a target. More
	 * specifically, it returns all the elements that have their bounding
	 * rectangle intersect with the given element's one, and are not passable
	 * for this element.
	 * 
	 * @param element
	 *            the element
	 * @param target
	 *            the target
	 * @return all the obstacles in the element's way
	 * @see Element#getAbsoluteRect()
	 * @see Element#isPassable(Game, Element)
	 */
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

	public void tryMoveByAndPush(Element element, Vector2D displacement) {
		List<Element> obstacles = getObstacles(element, element.getLocation().add(displacement));
		if (obstacles.stream().map(e -> tryMoveBy(e, displacement)).reduce(true, Boolean::logicalAnd)) {
			element.setLocation(element.getLocation().add(displacement));
		}
	}

	public void onClick(Player player, Vector2D target) {
		player.setTarget(getElements(target).stream().findFirst());
	}

	public void removeDynamicElement(Element element) {
		toRemove.add(element);
	}

	/**
	 * Removes any player that is associated with this client.
	 * 
	 * @param client
	 *            The client
	 */
	public void removePlayer(TcpClient client) {
		players.entrySet().stream().filter(e -> e.getValue().getClient() == client).findFirst().ifPresent(entry -> {
			removeDynamicElement(entry.getValue());
			players.remove(entry.getKey());
			boundIndices.remove(entry.getKey());
		});
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
		List<Element> obstacles = getObstacles(element, target);
		if (obstacles.isEmpty()) {
			element.setLocation(target);
			return true;
		}
		return false;
	}

	public boolean tryMoveBy(Element element, Vector2D displacement) {
		return getObstaclesFromMoveBy(element, displacement).isEmpty();
	}

	public List<Element> getObstaclesFromMoveBy(Element element, Vector2D displacement) {
		Vector2D target = element.getLocation().add(displacement);
		List<Element> obstacles = getObstacles(element, target);
		if (obstacles.isEmpty()) {
			element.setLocation(target);
		}
		return obstacles;
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
			elements.sort((a, b) -> a.getDepth().compareTo(b.getDepth()));
		}
		toAdd.clear();
		toRemove.clear();
	}

	private int getFreeIndex() {
		int i = 0;
		while (boundIndices.contains(i)) {
			i++;
		}
		return i;
	}

}
