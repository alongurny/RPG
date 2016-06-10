package rpg.graphics;

import java.awt.Graphics2D;

public class Translate extends Drawer {

	private double dx, dy;

	public Translate(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	@Override
	public void draw(Graphics2D g) {
		g.translate(dx, dy);
	}

	public Translate negate() {
		return new Translate(-dx, -dy);
	}

	@Override
	public String represent() {
		return String.format("%s %f:double %f:double", getClass().getName(), dx, dy);
	}

}
