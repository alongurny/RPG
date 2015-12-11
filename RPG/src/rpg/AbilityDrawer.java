package rpg;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import rpg.ui.Drawable;

public class AbilityDrawer implements Drawable {
	private AbilityHandler handler;
	private int x, y;

	public AbilityDrawer(int x, int y, AbilityHandler handler) {
		this.handler = handler;
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw(Graphics g) {
		for (Pair<Ability, Double> p : handler.getAbilities()) {
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, 32, 32);
			p.getFirst().draw(g);
			if (p.getSecond() < p.getFirst().getCooldown()) {
				g.setColor(new Color(63, 63, 63, 100));
				g.fillRect(0, 0, Ability.WIDTH, Ability.HEIGHT);
				int dx = (Ability.WIDTH / 2 - 10);
				int dy = (Ability.HEIGHT / 2 + 8);
				g.setColor(Color.BLACK);
				g.setFont(Font.decode("arial-bold-12"));
				g.drawString(String.format("%.2g", p.getFirst().getCooldown() - p.getSecond()), dx, dy);
			} else if (!handler.isCastable(p.getFirst())) {
				g.setColor(new Color(63, 63, 200, 100));
				g.fillRect(0, 0, Ability.WIDTH, Ability.HEIGHT);
			}
		}
	}

	@Override
	public int getIndex() {
		return 0;
	}

}
