package rpg.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import rpg.ability.Ability;
import rpg.element.entity.Player;
import rpg.logic.Game;

public class AbilityHandlerDrawer implements Drawable {

	private static Point[] ps = { new Point(16, 0), new Point(32, 0), new Point(32, 16), new Point(32, 32),
			new Point(16, 32), new Point(0, 32), new Point(0, 16), new Point(0, 0) };

	private Game game;
	private int num;

	public AbilityHandlerDrawer(Game game, int num) {
		this.num = num;
		this.game = game;
	}

	@Override
	public void draw(Graphics g) {
		Player p = game.getLevel().getPlayer(num);
		for (Ability ability : p.getAbilities()) {
			double cooldown = ability.getCooldown();
			ability.draw(g);
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, 32, 32);
			if (cooldown > 0) {
				g.setColor(new Color(63, 63, 63, 100));
				double percentage = 1 - cooldown / ability.getNumber("maxCooldown");
				boolean first = true;
				for (int i = 0; i < 8; i++) {
					double diff = (i + 1) / 8.0 - percentage;
					if (diff >= 0) {
						g.fillPolygon(
								new int[] { first ? (int) (ps[i].x + (1 - 8 * diff) * (ps[(i + 1) % 8].x - ps[i].x))
										: ps[i].x, 16, ps[(i + 1) % 8].x },
								new int[] { first ? (int) (ps[i].y + (1 - 8 * diff) * (ps[(i + 1) % 8].y - ps[i].y))
										: ps[i].y, 16, ps[(i + 1) % 8].y },
								3);
						first = false;
					}
				}
				int dx = (Ability.WIDTH / 2 - 4);
				int dy = (Ability.HEIGHT / 2 + 6);
				g.setColor(Color.BLACK);
				g.setFont(Font.decode("arial-bold-12"));
				String s;
				if (cooldown >= 1) {
					s = "" + ((int) cooldown);
				} else if (cooldown >= 0.1) {
					s = String.format("%.1g", cooldown);
				} else {
					s = "0";
				}
				g.drawString(s, dx, dy);
			} else if (!p.isCastable(ability)) {
				g.setColor(new Color(63, 63, 200, 100));
				g.fillRect(0, 0, Ability.WIDTH, Ability.HEIGHT);
			}
			g.translate(48, 0);
		}
	}
}
