package rpg.logic;

import java.util.ArrayList;
import java.util.List;

import rpg.element.Element;
import rpg.geometry.Vector2D;

public class Grid {

	private ElementContainer[][] elements;

	public Grid(int rows, int cols) {
		elements = new ElementContainer[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				elements[i][j] = new ElementContainer();
			}
		}
	}

	public void add(Element e) {
		elements[getRow(e)][getColumn(e)].add(e);
	}

	public List<Element> get(int row, int column) {
		return new ArrayList<>(elements[row][column]);
	}

	public int getColumn(Element e) {
		return (int) (e.getLocation().getX() / getColumnWidth());
	}

	public int getColumns() {
		return elements[0].length;
	}

	public int getColumnWidth() {
		return 32;
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

	public int getHeight() {
		return getRowHeight() * getRows();
	}

	public Vector2D getLocation(int row, int column) {
		return new Vector2D(getColumnWidth() * column, getRowHeight() * row);
	}

	public int getRow(Element e) {
		return (int) (e.getLocation().getY() / getRowHeight());
	}

	public int getRowHeight() {
		return 32;
	}

	public int getRows() {
		return elements.length;
	}

	public int getWidth() {
		return getColumnWidth() * getColumns();
	}

	public boolean remove(Element e) {
		return elements[getRow(e)][getColumn(e)].remove(e);
	}

}
