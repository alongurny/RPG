package rpg.ability;

import rpg.element.Element;
import rpg.element.Entity;
import rpg.graphics.draw.Drawer;
import rpg.graphics.draw.IconDrawer;
import rpg.logic.level.Level;

public class HasteSpell extends DurationAbility {

	private double speed;
	private Drawer drawer;

	public HasteSpell() {
		super(2, 2);
		drawer = new IconDrawer("img/haste.png", 32, 32);
	}

	@Override
	public void onStart(Level level, Entity caster, Element... elements) {
		caster.remove("mana", 1);
		speed = caster.getDouble("speed", 0);
		caster.set("speed", speed + 0.5 * caster.getTotalNumber("speed"));
	}

	@Override
	public void onEnd(Level level, Entity caster, Element... elements) {
		caster.set("speed", speed);
	}

	@Override
	public boolean isCastable(Entity caster, Element... elements) {
		return caster.isAlive() && caster.getDouble("mana") >= 1;
	}

	@Override
	public Drawer getSelfDrawer() {
		return drawer;
	}

}
