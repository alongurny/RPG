package rpg;

import physics.Vector;

public abstract class Element {

	private Vector location;

	public Element(Vector location) {
		this.location = location;
	}

	public abstract void update(Game game);

	public Vector getLocation() {
		return location;
	}

	public void setLocation(Vector location) {
		this.location = location;
	}

	public abstract void onInteraction(Game game, Element other);

}
