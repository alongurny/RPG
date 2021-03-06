package rpg.ability.air;

import external.Messages;
import rpg.ability.Ability;
import rpg.element.entity.Entity;
import rpg.graphics.Drawer;
import rpg.logic.level.Game;

public class FlightSpell extends Ability {

	private Drawer drawer = Messages.getTileDrawer("FlightSpell");
	private boolean active;
	private double duration = 10;

	public FlightSpell() {
		super(12, 8);
	}

	@Override
	public Drawer getSelfDrawer() {
		return drawer;
	}

	@Override
	protected boolean isActive(Game game, Entity caster) {
		return active;
	}

	@Override
	protected boolean isCastable(Entity caster) {
		return caster.isAlive();
	}

	@Override
	protected void onCast(Game game, Entity caster) {
		active = true;
		caster.addEffect("flying");
		game.addTimer(duration, () -> active = false);
	}

	@Override
	protected void onEnd(Game game, Entity caster) {
		caster.removeEffect("flying");
	}

	@Override
	protected void onUpdate(Game game, Entity caster) {

	}

}
