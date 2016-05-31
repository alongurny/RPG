package rpg.graphics;

import java.awt.Graphics2D;

import rpg.Thing;

public abstract class Drawer extends Thing {

	protected abstract String represent();

	public abstract void draw(Graphics2D g);

	@Override
	public String toString() {
		return represent();
	}

	public Drawer andThen(Drawer other) {
		Drawer obj = this;
		return new Drawer() {

			@Override
			public String represent() {
				return obj.represent() + ";;" + other.represent();
			}

			@Override
			public void draw(Graphics2D g) {
				obj.draw(g);
				other.draw(g);
			}
		};
	}

}
