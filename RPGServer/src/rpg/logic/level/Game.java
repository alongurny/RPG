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
import rpg.element.Depth;
import rpg.element.Element;
import rpg.element.Interactive;
import rpg.element.entity.Entity;
import rpg.element.entity.Player;
import rpg.element.entity.profession.Profession;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.logic.Grid;
import rpg.logic.Timer;
import rpg.server.GameServer;

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
	 *            the initial location to add
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
	 * 
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

	public List<Element> getDynamicElements() {
		return new ArrayList<>(elements);
	}

	/**
	 * Returns all the elements (dynamic and static) at the given location. More
	 * specifically, Returns all the elements that have their bounding rectangle
	 * contains the target.
	 * 
	 * @param target
	 *            the location
	 * @return all the elements e such that e.getAbsoluteRect().contains(target)
	 */
	public List<Element> getElementsAt(Vector2D target) {
		List<Element> elements = new ArrayList<>(this.elements);
		elements.addAll(getStaticElements());
		elements.removeIf(e -> !e.getAbsoluteRect().contains(target));
		return elements;
	}

	/**
	 * Returns the grid of this game. All the static elements are contained in
	 * the grid.
	 * 
	 * @return the grid
	 */
	public Grid getGrid() {
		return grid;
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

	/**
	 * Returns the player that represents the given client, wrapped in an
	 * optional, or nothing if no player is found.
	 * 
	 * @param client
	 *            the client
	 * @return an optional containing the player that represents the client, or
	 *         nothing if there is no such player
	 */
	public Optional<Player> getPlayer(TcpClient client) {
		return players.values().stream().filter(p -> p.getClient() == client).findFirst();
	}

	/**
	 * Returns all the static elements of this game. Static elements are
	 * guaranteed not to move or change through the game and so, their drawables
	 * are sent only once to the client, and they are added to the {@link Grid}.
	 * 
	 * @return all the static elements of this game
	 */
	public List<Element> getStaticElements() {
		return grid.getElements();
	}

	private void handleCollisions() {
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

	/**
	 * Indicates if this game is ready to play. Implementation may be different
	 * for different games, but normally it will indicate if at least one player
	 * has connected.
	 * 
	 * @return <code>true</code> if there are enough players to start the game,
	 *         <code>false</code> otherwise
	 */
	public boolean isReady() {
		return boundIndices.size() >= 1;
	}

	/**
	 * Called when in the client side, the client clicked on the screen.
	 * 
	 * @param player
	 *            the player that its client clicked on the screen
	 * @param target
	 *            the location of the click, relative to this game
	 */
	public void onClick(Player player, Vector2D target) {
		player.setTarget(getElementsAt(target).stream().findFirst());
	}

	/**
	 * Removes a dynamic element from the game.
	 * 
	 * @param element
	 *            the dynamic element to remove
	 */
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

	/**
	 * Tries to perform an interaction for the given entity. An interaction can
	 * occur only for {@link Interactive} elements. The first interactive that
	 * will be {@link Interactive#isInteractable(Game, Entity) interactable}
	 * will have its {@link Interactive#onInteract(Game, Entity) onInteract}
	 * method called and cause this method to return <code>true</code>. If no
	 * such interactive is found, this method will return <code>false</code>.
	 * otherwise.
	 * 
	 * @param entity
	 *            the entity that performs the interaction
	 * @return <code>true</code> if the interaction succeeded and
	 *         <code>false</code> otherwise
	 */
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

	/**
	 * Tries to move this element to the given target. This is possible only if
	 * there are no obstacles in the way.
	 * 
	 * @param element
	 *            the element to move
	 * @param target
	 *            the target location
	 * @return <code>true</code> if the trial succeeded and <code>false</code>
	 *         if there were obstacles
	 */
	public boolean tryMove(Element element, Vector2D target) {
		List<Element> obstacles = getObstacles(element, target);
		if (obstacles.isEmpty()) {
			element.setLocation(target);
			return true;
		}
		return false;
	}

	/**
	 * Tries to move this element by the given displacement. This is possible
	 * only if there are no obstacles in the way.
	 * 
	 * @param element
	 *            the element to move
	 * @param displacement
	 *            the displacement
	 * @return <code>true</code> if the trial succeeded and <code>false</code>
	 *         if there were obstacles
	 */
	public boolean tryMoveBy(Element element, Vector2D displacement) {
		return getObstaclesFromMoveBy(element, displacement).isEmpty();
	}

	/**
	 * Tries to move this element by the given displacement. This is possible
	 * only if there are no obstacles in the way. Returns a list of the
	 * obstacles.
	 * 
	 * @param element
	 *            the element to move
	 * @param displacement
	 *            the displacement
	 * @return a {@link List} of the obstacles.
	 */
	public List<Element> getObstaclesFromMoveBy(Element element, Vector2D displacement) {
		Vector2D target = element.getLocation().add(displacement);
		List<Element> obstacles = getObstacles(element, target);
		if (obstacles.isEmpty()) {
			element.setLocation(target);
		}
		return obstacles;
	}

	/**
	 * This method is called repeatedly by the {@link GameServer}. It updates
	 * many things: it calls {@link Element#update(Game, double) update} on its
	 * elements, it actually starts drawing the elements that were added by
	 * {@link #addDynamicElement(Element) addDynamicElement} and
	 * {@link #addStaticElement(Element) addStaticElement}, it updates the
	 * timer, checks that entities do not reference missing elements and sorts
	 * the elements by their {@link Depth}.
	 * 
	 * @param dt
	 *            the time that passed since the last invocation of this method
	 */
	public void update(double dt) {
		elements.forEach(e -> e.update(this, dt));
		handleCollisions();
		timer.update(this, dt);
		elements.addAll(toAdd);
		elements.removeAll(toRemove);
		elements.stream().filter(e -> e instanceof Entity).map(e -> (Entity) e).forEach(p -> {
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

	/**
	 * Returns a free index for a player to join.
	 * 
	 * @return a free index for a player to join
	 */
	private int getFreeIndex() {
		int i = 0;
		while (boundIndices.contains(i)) {
			i++;
		}
		return i;
	}

}
