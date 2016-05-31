package rpg.graphics.draw;

import java.awt.Graphics;

import rpg.Thing;
import rpg.graphics.Translate;

public abstract class Drawer extends Thing implements Drawable {

	public static Drawer translate(int dx, int dy) {
		return new Translate(dx, dy);
	}

	public abstract String represent();

	public Drawer andThen(Drawer other) {
		Drawer tis = this;
		return new Drawer() {
			@Override
			public String represent() {
				return this.represent() + "\n" + other.represent();
			}

			public void draw(Graphics g) {
				tis.draw(g);
				other.draw(g);
			}
		};
	}

	public static Drawer empty() {
		return new Drawer() {

			@Override
			public void draw(Graphics g) {
			}

			@Override
			public String represent() {
				return "";
			}
		};
	}

}
