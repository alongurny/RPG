package rpg.ability;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import rpg.Mechanism;
import rpg.Requirement;
import rpg.element.entity.Entity;
import rpg.logic.Level;
import rpg.ui.Drawable;

public class AbilityHandler extends Mechanism implements Drawable {

	private List<Ability> abilities;
	private Entity caster;

	private static Point[] ps = { new Point(16, 0), new Point(32, 0), new Point(32, 16), new Point(32, 32),
			new Point(16, 32), new Point(0, 32), new Point(0, 16), new Point(0, 0) };

	public AbilityHandler(Entity caster) {
		this.caster = caster;
		abilities = new CopyOnWriteArrayList<>();
	}

	public void update(Level level, double dt) {
		abilities.forEach(p -> p.update(level, dt));
	}

	public void addAbility(Ability ability) {
		abilities.add(ability);
	}

	public Ability getAbility(int i) {
		return abilities.get(i);
	}

	public List<Ability> getAbilities() {
		return new ArrayList<>(abilities);
	}

	public boolean makeCastable(Ability ability) {
		boolean noCooldown = ability.getCooldown() == 0;
		if (noCooldown) {
			for (Requirement r : ability.getRequirements()) {
				if (!r.isRequireable(caster)) {
					return false;
				}
			}
			for (Requirement r : ability.getRequirements()) {
				r.require(caster);
			}
			return true;
		}

		return false;
	}

	public boolean isCastable(Ability ability) {
		boolean noCooldown = ability.getCooldown() == 0;
		if (noCooldown) {
			for (Requirement r : ability.getRequirements()) {
				if (!r.isRequireable(caster)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public boolean tryCast(Level level, Ability ability) {
		if (makeCastable(ability)) {
			ability.onCast(level, caster);
			ability.setCooldown(ability.getContinuous("maxCooldown"));
			return true;
		}
		return false;
	}

	public int getSize() {
		return abilities.size();
	}

	public boolean tryCast(Level level, int i) {
		return tryCast(level, getAbility(i));
	}

	@Override
	public void draw(Graphics g) {
		for (Ability ability : abilities) {
			double cooldown = ability.getCooldown();
			ability.draw(g);
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, 32, 32);
			if (cooldown > 0) {
				g.setColor(new Color(63, 63, 63, 100));
				double percentage = 1 - cooldown / ability.getContinuous("maxCooldown");
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
			} else if (!isCastable(ability)) {
				g.setColor(new Color(63, 63, 200, 100));
				g.fillRect(0, 0, Ability.WIDTH, Ability.HEIGHT);
			}
			g.translate(48, 0);
		}
	}

}
