package rpg.graphics.draw;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import rpg.ability.Ability;
import rpg.element.Player;

public class MultiAbilityDrawer extends Drawer {

	private List<Drawer> drawers;

	public MultiAbilityDrawer() {
		drawers = new ArrayList<>();
	}

	public void addAbility(Player player, Ability ability) {
		drawers.add(ability.getDrawer(player));
	}

	@Override
	public void draw(Graphics g) {
		for (Drawer drawer : drawers) {
			drawer.draw(g);
			g.translate(48, 0);
		}
		g.translate(-48 * drawers.size(), 0);
	}
}
