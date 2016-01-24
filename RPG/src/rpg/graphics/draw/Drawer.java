package rpg.graphics.draw;

import java.awt.Graphics;

import rpg.Thing;

public abstract class Drawer extends Thing implements Drawable {

	public static Drawer concat(Drawer... drawers) {
		return new Drawer() {
			@Override
			public void draw(Graphics g) {
				for (Drawer drawer : drawers) {
					drawer.draw(g);
				}
			}
		};
	}

}
