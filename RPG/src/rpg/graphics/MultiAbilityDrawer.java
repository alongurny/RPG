package rpg.graphics;

import java.util.ArrayList;
import java.util.List;

import rpg.ability.Ability;
import rpg.element.Player;

public class MultiAbilityDrawer {

	private List<Drawer> drawers;
	private double dx, dy;

	public MultiAbilityDrawer(double dx, double dy) {
		drawers = new ArrayList<>();
		this.dx = dx;
		this.dy = dy;
	}

	public void addAbility(Player player, Ability ability) {
		drawers.add(ability.getDrawer(player));
	}

	public Drawer getDrawer() {
		return drawers.stream().map(d -> d.andThen(new Translate(48, 0))).reduce(new Translate(dx, dy), Drawer::andThen)
				.andThen(new Translate(-48 * drawers.size(), 0)).andThen(new Translate(-dx, -dy));
	}

}
