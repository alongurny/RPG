package rpg.graphics;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Sprite extends Drawer {

	private List<Drawer> drawers;
	private int index;

	public Sprite(List<? extends Drawer> drawers) {
		this.drawers = new ArrayList<>(drawers);
	}

	@Override
	public void draw(Graphics2D g) {
		drawers.get(index).draw(g);
	}

	@Override
	public String represent() {
		return drawers.get(index).represent();
	}

	public void step() {
		index = (index + 1) % drawers.size();
	}

}
