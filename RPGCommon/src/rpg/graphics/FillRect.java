package rpg.graphics;

import java.awt.Graphics2D;

public class FillRect extends Drawer {

	private double width, height;

	public FillRect(double width, double height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void draw(Graphics2D g) {
		g.fillRect(0, 0, (int) width, (int) height);
	}

	@Override
	public String represent() {
		return String.format("%s %f:double %f:double", getClass().getName(), width, height);
	}
}
