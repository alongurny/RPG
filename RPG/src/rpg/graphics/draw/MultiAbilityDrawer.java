package rpg.graphics.draw;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class MultiAbilityDrawer extends Drawer {

	private List<Drawer> drawers;

	public MultiAbilityDrawer() {
		drawers = new ArrayList<>();
	}

	public void addAbilityDrawer(Drawer drawer) {
		drawers.add(drawer);
	}

	@Override
	public void draw(Graphics g) {
		for (Drawer drawer : drawers) {
			drawer.draw(g);
			g.translate(48, 0);
		}
	}
}
