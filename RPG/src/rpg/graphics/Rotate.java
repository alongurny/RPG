package rpg.graphics;

import java.awt.Graphics2D;

public class Rotate extends Drawer {

	private double angle;

	public Rotate(double angle) {
		this.angle = angle;
	}

	@Override
	public void draw(Graphics2D g) {
		g.rotate(angle);
	}

	@Override
	public String represent() {
		return String.format("%s %f:double", getClass().getName(), angle);
	}

}
