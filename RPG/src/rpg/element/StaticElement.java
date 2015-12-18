package rpg.element;

import java.awt.Rectangle;

import rpg.Map;
import rpg.level.Level;
import rpg.physics.Vector2D;
import rpg.ui.Drawable;

public abstract class StaticElement extends Element implements Drawable {

	private int row, column;
	private int rowSpan, columnSpan;

	public StaticElement(int row, int column, int rowSpan, int columnSpan) {
		this.row = row;
		this.column = column;
		this.rowSpan = rowSpan;
		this.columnSpan = columnSpan;
	}

	public StaticElement(int row, int column) {
		this(row, column, 1, 1);
	}

	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	@Override
	public void onCollision(Level level, Element other) {
	}

	public static Vector2D getLocation(Level level, int row, int column) {
		Map map = level.getMap();
		int height = map.getRowHeight();
		int width = map.getColumnWidth();
		return new Vector2D(column * width, row * height);
	}

	public static Rectangle getRect(Level level, StaticElement s) {
		Map map = level.getMap();
		int height = map.getRowHeight();
		int width = map.getColumnWidth();
		return new Rectangle(s.getColumn() * width, s.getRow() * height, s.getColumnSpan() * width,
				s.getColumnSpan() * height);
	}

	@Override
	public String toString() {
		String sy = rowSpan == 1 ? row + "" : row + "-" + (row + rowSpan - 1);
		String sx = columnSpan == 1 ? column + "" : column + "-" + (column + columnSpan - 1);
		return String.format("%s (%s, %s)", super.toString(), sy, sx);
	}

	public int getRowSpan() {
		return rowSpan;
	}

	public int getColumnSpan() {
		return columnSpan;
	}
}
