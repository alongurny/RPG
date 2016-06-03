package rpg.ability.force;

import java.util.Optional;

import rpg.ability.DurationAbility;
import rpg.ability.TargetType;
import rpg.element.Element;
import rpg.element.Entity;
import rpg.element.entity.Attribute;
import rpg.graphics.DrawIcon;
import rpg.graphics.Drawer;
import rpg.logic.level.Level;

public class HasteSpell extends DurationAbility {

	private Drawer drawer;

	public HasteSpell() {
		super(8, 2, TargetType.SELF);
		drawer = new DrawIcon("img/haste.png", 32, 32);
	}

	@Override
	public void onStart(Level level, Entity caster, Optional<Element> element) {
		caster.subtractMana(5);
		caster.addAttribute(Attribute.DEX, 2);
	}

	@Override
	public void onEnd(Level level, Entity caster, Optional<Element> element) {
		caster.subtractAttribute(Attribute.DEX, 2);
	}

	@Override
	public boolean isCastable(Entity caster, Optional<Element> element) {
		return caster.isAlive() && caster.getMana() >= 5;
	}

	@Override
	public Drawer getSelfDrawer() {
		return drawer;
	}

}
