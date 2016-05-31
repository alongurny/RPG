package rpg.graphics;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import rpg.graphics.draw.Drawer;

public class Sprite extends Drawer {

	private List<Drawer> drawers;
	private int index;

	public Sprite(List<? extends Drawer> drawers) {
		this.drawers = new ArrayList<>(drawers);
	}

	public void step() {
		index = (index + 1) % drawers.size();
	}

	@Override
	public void draw(Graphics g) {
		drawers.get(index).draw(g);
	}

	@Override
	public String represent() {
		return drawers.get(index).represent();
	}

}
