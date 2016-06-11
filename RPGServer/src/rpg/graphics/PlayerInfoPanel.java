package rpg.graphics;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import rpg.ability.Ability;
import rpg.element.entity.Player;

public class PlayerInfoPanel {

	private List<Drawer> drawers;
	private double dx, dy;

	public PlayerInfoPanel(double xp, double dx, double dy) {
		drawers = new ArrayList<>();
		this.dx = dx;
		this.dy = dy;
		drawers.add(new SetColor(Color.GREEN).andThen(new SetFont("Arial", Font.PLAIN, 24))
				.andThen(new DrawString("XP=" + (int) xp, -108, 0)));
	}

	public void addAbility(Player player, Ability ability) {
		drawers.add(ability.getDrawer(player));
	}

	public Drawer getDrawer() {
		return drawers.stream().map(d -> d.andThen(new Translate(48, 0))).reduce(new Translate(dx, dy), Drawer::andThen)
				.andThen(new Translate(-48 * drawers.size(), 0)).andThen(new Translate(-dx, -dy));
	}

}
