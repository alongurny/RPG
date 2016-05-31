package rpg.graphics;

import java.awt.Color;
import java.awt.Graphics2D;

public class ScaleDrawer extends Drawer {

	private Color main, secondary;
	private double percentage;
	private double width;
	private double height;

	public ScaleDrawer(double percentage, Color main, Color secondary, double width, double height) {
		this.percentage = percentage;
		this.main = main;
		this.secondary = secondary;
		this.width = width;
		this.height = height;
	}

	public ScaleDrawer(double percentage, int main, int secondary, double width, double height) {
		this(percentage, new Color(main), new Color(secondary), width, height);
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(main);
		g.translate(-width / 2, -height / 2);
		g.fillRect(0, 0, (int) (width * percentage), (int) height);
		g.setColor(secondary);
		g.fillRect((int) (width * percentage), 0, (int) (width * (1 - percentage)), (int) height);
		g.translate(width / 2, height / 2);
	}

	@Override
	public String represent() {
		return String.format("%s %f:double %d:int %d:int %f:double %f:double", getClass().getName(), percentage,
				main.getRGB(), secondary.getRGB(), width, height);
	}
}
