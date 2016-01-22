package rpg.ability;

import java.util.Arrays;
import java.util.List;

import rpg.Cost;
import rpg.Requirement;
import rpg.element.Entity;
import rpg.graphics.draw.Drawer;
import rpg.graphics.draw.IconDrawer;
import rpg.logic.level.Level;

public class HasteSpell extends Spell {

	private double speed;
	private Drawer drawer;

	public HasteSpell() {
		super(2, 2);
		drawer = new IconDrawer("img/haste.png", 32, 32);
	}

	public Drawer getAbilityDrawer() {
		return new IconDrawer("haste.png", 32, 32);
	}

	@Override
	public void onStart(Level level, Entity caster) {
		speed = caster.getDouble("speed", 0);
		caster.set("speed", speed + 0.5 * caster.getTotalNumber("speed"));
	}

	@Override
	public void onEnd(Level level, Entity caster) {
		caster.set("speed", speed);
	}

	@Override
	public List<Requirement> getRequirements() {
		return Arrays.asList(Requirement.atLeast("mana", 1), Entity::isAlive);
	}

	@Override
	public List<Cost> getCosts() {
		return Arrays.asList(Cost.bar("mana", 1));
	}

	@Override
	public Drawer getSelfDrawer() {
		return drawer;
	}

}
