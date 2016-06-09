package rpg.ability.force;

import rpg.ability.Ability;
import rpg.element.entity.Attribute;
import rpg.element.entity.Entity;
import rpg.graphics.DrawIcon;
import rpg.graphics.Drawer;
import rpg.logic.level.Game;

public class HasteSpell extends Ability {

	private Drawer drawer;
	private double duration;
	private boolean active;

	public HasteSpell() {
		super(8, 5);
		drawer = new DrawIcon("img/haste.png", 32, 32);
		duration = 5;
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
	public void onCast(Game game, Entity caster) {
		active = true;
		game.addTimer(duration, () -> active = false);
		caster.addAttribute(Attribute.DEX, 2);
	}

	@Override
	public void onEnd(Game game, Entity caster) {
		caster.subtractAttribute(Attribute.DEX, 2);
	}

	@Override
	protected boolean isActive(Game game, Entity caster) {
		return active;
	}

	@Override
	protected void onUpdate(Game game, Entity caster) {
	}

}
