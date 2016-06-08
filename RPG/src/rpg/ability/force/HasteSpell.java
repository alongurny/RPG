package rpg.ability.force;

import rpg.ability.DurationAbility;
import rpg.element.Entity;
import rpg.element.entity.Attribute;
import rpg.graphics.DrawIcon;
import rpg.graphics.Drawer;
import rpg.logic.level.Game;

public class HasteSpell extends DurationAbility {

	private Drawer drawer;

	public HasteSpell() {
		super(8, 5, 5);
		drawer = new DrawIcon("img/haste.png", 32, 32);
	}

	@Override
	public Drawer getSelfDrawer() {
		return drawer;
	}

	@Override
	public boolean isCastable(Entity caster) {
		return caster.isAlive();
	}

	@Override
	public void onEnd(Game game, Entity caster) {
		caster.subtractAttribute(Attribute.DEX, 2);
	}

	@Override
	public void onStart(Game game, Entity caster) {
		caster.addAttribute(Attribute.DEX, 2);
	}

	@Override
	protected void onUpdate(Game game, Entity caster) {

	}

}
