package rpg.ability;

import rpg.element.Element;
import rpg.element.Entity;
import rpg.element.Fireball;
import rpg.geometry.Vector2D;
import rpg.graphics.IconDrawer;
import rpg.graphics.draw.Drawer;
import rpg.logic.level.Level;

public class FireballSpell extends Ability {

	private Drawer drawer;

	public FireballSpell() {
		super(2);
		set("speed", 192);
		drawer = new IconDrawer("img/fireball.gif", 32, 32);
	}

	public boolean isCastable(Entity caster, Element... elements) {
		return elements.length == 1 && caster.isAlive() && elements[0] instanceof Entity
				&& caster.getDouble("mana") >= 1;
	}

	@Override
	public void onCast(Level level, Entity caster, Element... elements) {
		caster.remove("mana", 1);
		Vector2D location = caster.getLocation();
		Vector2D direction = elements[0].getLocation().subtract(location).getUnitalVector();
		level.addDynamicElement(new Fireball(caster, location, direction, getDouble("speed")));
	}

	@Override
	public Drawer getSelfDrawer() {
		return drawer;
	}
}
