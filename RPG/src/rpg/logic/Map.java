package rpg.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import rpg.element.Air;
import rpg.element.Element;
import rpg.physics.Vector2D;

public class Map {

	private Element[][] staticElements;

	public Map(int rows, int cols) {
		staticElements = new Element[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				staticElements[i][j] = new Air(getLocation(i, j));
			}
		}
	}

	public List<Element> getElements() {
		List<Element> list = new ArrayList<>();
		for (int i = 0; i < staticElements.length; i++) {
			for (int j = 0; j < staticElements[i].length; j++) {
				list.add(staticElements[i][j]);
			}
		}
		return list;
	}

	public void put(Element e) {
		staticElements[getRow(e)][getColumn(e)] = Objects.requireNonNull(e);
	}

	public int getRow(Element e) {
		return (int) (e.getLocation().getY() / getRowHeight());
	}

	public int getColumn(Element e) {
		return (int) (e.getLocation().getX() / getColumnWidth());
	}

	public Element get(int y, int x) {
		return staticElements[y][x];
	}

	public int getRows() {
		return staticElements.length;
	}

	public int getColumns() {
		return staticElements[0].length;
	}

	public int getRowHeight() {
		return 32;
	}

	public int getColumnWidth() {
		return 32;
	}

	public int getWidth() {
		return getColumnWidth() * getColumns();
	}

	public int getHeight() {
		return getRowHeight() * getRows();
	}

	public Vector2D getLocation(int row, int column) {
		return new Vector2D(getColumnWidth() * column, getRowHeight() * row);
	}

}
