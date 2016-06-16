package rpg.element.entity.profession;

import external.Messages;
import rpg.ability.Ability;
import rpg.element.entity.Entity;
import rpg.element.entity.MiniDragon;
import rpg.graphics.Drawer;
import rpg.logic.level.Game;

public class SummonMiniDragon extends Ability {

	protected SummonMiniDragon() {
		super(27, 20);
	}

	@Override
	protected Drawer getSelfDrawer() {
		return Messages.getTileDrawer("Dragon.img");
	}

	@Override
	protected boolean isActive(Game game, Entity caster) {
		return false;
	}

	@Override
	protected boolean isCastable(Entity caster) {
		return caster.isAlive();
	}

	@Override
	protected void onCast(Game game, Entity caster) {
		game.addDynamicElement(new MiniDragon(caster.getLocation().add(game.getGrid().getLocation(2, 0))));
	}

	@Override
	protected void onEnd(Game game, Entity caster) {

	}

	@Override
	protected void onUpdate(Game game, Entity caster) {

	}

}
