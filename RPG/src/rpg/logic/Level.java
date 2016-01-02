package rpg.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import rpg.Interactive;
import rpg.element.Element;
import rpg.element.entity.Entity;
import rpg.element.entity.Player;
import rpg.physics.Vector2D;
import rpg.ui.Rectangle;

public class Level {

	private List<Element> elements;
	private Grid grid;
	private boolean finished;
	private Level nextLevel;
	private Timer timer;

	public Level(int rows, int cols) {
		elements = new CopyOnWriteArrayList<>();
		grid = new Grid(rows, cols);
		timer = new Timer();
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

	private List<Element> getObstacles(Element element, Vector2D target) {
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
		for (Element e : getNearElements(element)) {
			if (e != element) {
				if (e.getAbsoluteRect().intersects(newRect) && !e.isPassable(this, element)) {
					obstacles.add(e);
				}
			}
		}
		return obstacles;
	}

	public void replaceDynamicElement(List<Element> elements) {
		this.elements.clear();
		this.elements.addAll(elements);
	}

	public List<Element> getDynamicElements() {
		return new ArrayList<>(elements);
	}

	public void addDynamicElement(Element element) {
		elements.add(element);
	}

	public boolean removeDynamicElement(Element element) {
		return elements.remove(element);
	}

	public void update(double d) {
		elements.forEach(e -> e.update(this, d));
		handleCollisions();
		timer.update(this, d);
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
			for (Element s : getNearElements(e)) {
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
		for (Element e : getNearElements(entity)) {
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

	public Grid getMap() {
		return grid;
	}

	public Level getNextLevel() {
		return nextLevel;
	}

	public void setNextLevel(Level nextLevel) {
		this.nextLevel = nextLevel;
	}

	public List<Element> getNearElements(
			Element e) { /*
							 * int y = e., x; List<StaticElement> list = new
							 * ArrayList<>(); for (int i = -1; i <= 1; i++) {
							 * for (int j = -1; j <= 1; j++) { if (0 <= y + i &&
							 * y + i <= map.getRows()) { if (0 <= x + j && x + j
							 * <= map.getColumns()) { list.add(map.get(y + i, x
							 * + j)); } } } }
							 */
		return grid.getElements();
	}

	public List<Element> getStaticElements() {
		return grid.getElements();
	}

	public boolean removeStaticElement(Element e) {
		return grid.remove(e);
	}

	public void addTimer(double time, Runnable run) {
		timer.add(time, run);
	}

	public Player getPlayer(int index) {
		System.out.println(index + ", " + elements.size());
		for (Element e : elements) {
			if (e instanceof Player) {
				if (index == 0) {
					return (Player) e;
				}
				index--;
			}
		}
		throw new IndexOutOfBoundsException();
	}
}
