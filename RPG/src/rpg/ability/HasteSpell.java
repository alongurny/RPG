package rpg.ability;

import rpg.element.Element;
import rpg.element.Entity;
import rpg.graphics.DrawIcon;
import rpg.graphics.Drawer;
import rpg.logic.level.Level;

public class HasteSpell extends DurationAbility {

	private Drawer drawer;

	public HasteSpell() {
		super(2, 2, TargetType.SELF);
		drawer = new DrawIcon("img/haste.png", 32, 32);
	}

	@Override
	public void onStart(Level level, Entity caster, Element... elements) {
		caster.subtractMana(20);
		caster.setSpeed(caster.getSpeed() * 1.25);
	}

	@Override
	public void onEnd(Level level, Entity caster, Element... elements) {
		caster.setSpeed(caster.getSpeed() * 0.8);
	}

	@Override
	public boolean isCastable(Entity caster, Element... elements) {
		return caster.isAlive() && caster.getMana() >= 20;
	}

	@Override
	public Drawer getSelfDrawer() {
		return drawer;
	}

}
