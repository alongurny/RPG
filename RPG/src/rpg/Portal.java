package rpg;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Objects;

import physics.Vector;

public class Portal extends VisualElement implements Usable {

	public static Pair<Portal, Portal> getPair(Vector location1, Vector location2) {
		Portal p1 = new Portal(location1, location2);
		Portal p2 = new Portal(location2, location1);
		return new Pair<>(p1, p2);
	}

	private Vector target;

	public Portal(Vector location, Vector target) {
		super(location);
		this.target = Objects.requireNonNull(target);
	}

	@Override
	public void onUse(Game game, Player player) {
		game.tryMove(player, target);
	}

	@Override
	public void update(Game game) {

	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInteraction(Game game, Element other) {

	}

	@Override
	public Rectangle getBoundingRect() {
		return null;
	}

}
