package rpg.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

public class AbilityDrawer extends Drawer {

	private static final int WIDTH = 32;
	private static final int HEIGHT = 32;

	private static Point[] ps = { new Point(16, 0), new Point(32, 0), new Point(32, 16), new Point(32, 32),
			new Point(16, 32), new Point(0, 32), new Point(0, 16), new Point(0, 0) };
	private double cooldown;
	private double maxCooldown;
	private boolean enoughMana;
	private boolean castable;

	public AbilityDrawer(double cooldown, double maxCooldown, boolean enoughMana, boolean castable) {
		this.cooldown = cooldown;
		this.maxCooldown = maxCooldown;
		this.enoughMana = enoughMana;
		this.castable = castable;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.translate(-16, -16);
		g.drawRect(0, 0, 32, 32);
		if (cooldown > 0) {
			g.setColor(new Color(63, 63, 63, 100));
			double percentage = 1 - cooldown / maxCooldown;
			boolean first = true;
			for (int i = 0; i < 8; i++) {
				double diff = (i + 1) / 8.0 - percentage;
				if (diff >= 0) {
					g.fillPolygon(
							new int[] {
									first ? (int) (ps[i].x + (1 - 8 * diff) * (ps[(i + 1) % 8].x - ps[i].x)) : ps[i].x,
									16, ps[(i + 1) % 8].x },
							new int[] {
									first ? (int) (ps[i].y + (1 - 8 * diff) * (ps[(i + 1) % 8].y - ps[i].y)) : ps[i].y,
									16, ps[(i + 1) % 8].y },
							3);
					first = false;
				}
			}
			int dx = (WIDTH / 2 - 4);
			int dy = (HEIGHT / 2 + 6);
			g.setColor(Color.BLACK);
			g.setFont(Font.decode("arial-bold-12"));
			String s;
			if (cooldown >= 1) {
				s = Integer.toString((int) cooldown);
			} else if (cooldown >= 0.1) {
				s = String.format("%.1g", cooldown);
			} else {
				s = "0";
			}
			g.drawString(s, dx, dy);
		} else if (!castable) {
			g.setColor(new Color(63, 63, 63, 100));
			g.fillRect(0, 0, 32, 32);
		}
		if (!enoughMana) {
			g.setColor(new Color(13, 0, 63, 100));
			g.fillRect(0, 0, 32, 32);
		}
		g.translate(16, 16);
	}

	@Override
	public String represent() {
		return String.format("%s %f:double %f:double %b:boolean %b:boolean", getClass().getName(), cooldown,
				maxCooldown, enoughMana, castable);
	}

}
