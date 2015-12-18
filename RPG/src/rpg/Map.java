package rpg;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import rpg.element.Air;
import rpg.element.StaticElement;

public class Map {

	private StaticElement[][] staticElements;

	public Map(int rows, int cols) {
		staticElements = new StaticElement[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				staticElements[i][j] = new Air(i, j);
			}
		}
	}

	public List<StaticElement> getElements() {
		List<StaticElement> list = new ArrayList<>();
		for (int i = 0; i < staticElements.length; i++) {
			for (int j = 0; j < staticElements[i].length; j++) {
				list.add(staticElements[i][j]);
			}
		}
		return list;
	}

	public void put(StaticElement e) {
		staticElements[e.getRow()][e.getColumn()] = Objects.requireNonNull(e);
	}

	public StaticElement get(int y, int x) {
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

}
