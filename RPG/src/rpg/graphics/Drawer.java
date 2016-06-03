package rpg.graphics;

import java.awt.Graphics2D;

public abstract class Drawer {

	public Drawer andThen(Drawer other) {
		Drawer obj = this;
		return new Drawer() {

			@Override
			public void draw(Graphics2D g) {
				obj.draw(g);
				other.draw(g);
			}

			@Override
			public String represent() {
				return obj.represent() + ";;" + other.represent();
			}
		};
	}

	public abstract void draw(Graphics2D g);

	protected abstract String represent();

	@Override
	public String toString() {
		return represent();
	}

}
