package rpg.logic;

import java.util.ArrayList;
import java.util.List;

import rpg.element.Air;
import rpg.element.Element;
import rpg.physics.Vector2D;

public class Grid {

	private ElementContainer[][] elements;

	public Grid(int rows, int cols) {
		elements = new ElementContainer[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				elements[i][j] = new ElementContainer();
				elements[i][j].add(new Air(getLocation(i, j)));
			}
		}
	}

	public List<Element> getElements() {
		List<Element> list = new ArrayList<>();
		for (int i = 0; i < elements.length; i++) {
			for (int j = 0; j < elements[i].length; j++) {
				list.addAll(elements[i][j]);
			}
		}
		return list;
	}

	public void add(Element e) {
		elements[getRow(e)][getColumn(e)].add(e);
	}

	public int getRow(Element e) {
		return (int) (e.getLocation().getY() / getRowHeight());
	}

	public int getColumn(Element e) {
		return (int) (e.getLocation().getX() / getColumnWidth());
	}

	public List<Element> get(int y, int x) {
		return new ArrayList<>(elements[y][x]);
	}

	public int getRows() {
		return elements.length;
	}

	public int getColumns() {
		return elements[0].length;
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

	public boolean remove(Element e) {
		return elements[getRow(e)][getColumn(e)].remove(e);
	}

}