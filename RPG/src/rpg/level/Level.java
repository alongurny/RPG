package rpg.level;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import rpg.Interactive;
import rpg.Map;
import rpg.element.Element;
import rpg.element.entity.Entity;
import rpg.physics.Vector2D;

public class Level {

	private List<Element> elements;
	private Map map;
	private boolean finished;
	private Level nextLevel;

	public Level(int rows, int cols) {
		elements = new CopyOnWriteArrayList<>();
		map = new Map(rows, cols);
	}

	public boolean tryMoveBy(Element element, Vector2D displacement) {
		return tryMove(element, element.getLocation().add(displacement));
	}

	public boolean tryMove(Element element, Vector2D target) {
		if (isPassable(element, target)) {
			element.setLocation(target);
			return true;
		}
		return false;
	}

	public void interact(Entity entity, Interactive interactive) {
		interactive.onInteract(this, entity);
	}

	private boolean isPassable(Element element, Vector2D target) {
		Rectangle oldRect = element.getAbsoluteRect();
		int x = (int) (target.getX() - element.getLocation().getX() + oldRect.getX());
		int y = (int) (target.getY() - element.getLocation().getY() + oldRect.getY());
		Rectangle newRect = new Rectangle(x, y, oldRect.width, oldRect.height);
		for (Element e : elements) {
			if (e.getAbsoluteRect().intersects(newRect) && !e.isPassable(this, element)) {
				return false;
			}
		}
		for (Element s : getNearElements(element)) {
			Rectangle r = s.getAbsoluteRect();
			if (r.intersects(newRect) && !s.isPassable(this, element)) {
				return false;
			}
		}
		return true;
	}

	public List<Element> getDynamicElements() {
		return new ArrayList<>(elements);
	}

	public void addElement(Element element) {
		elements.add(element);
	}

	public void removeElement(Element element) {
		elements.remove(element);
	}

	public void update() {
		elements.forEach(e -> e.update(this));
		handleCollisions();
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
		Rectangle r1 = e.getAbsoluteRect();
		Rectangle r2 = f.getAbsoluteRect();
		return r1.intersects(r2);
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

	public Map getMap() {
		return map;
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
								 * ArrayList<>(); for (int i = -1; i <= 1; i++)
								 * { for (int j = -1; j <= 1; j++) { if (0 <= y
								 * + i && y + i <= map.getRows()) { if (0 <= x +
								 * j && x + j <= map.getColumns()) {
								 * list.add(map.get(y + i, x + j)); } } } }
								 */
		return map.getElements();
	}
}
