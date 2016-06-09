package rpg.ability;

import rpg.element.entity.Entity;
import rpg.graphics.Drawer;
import rpg.graphics.TileDrawer;
import rpg.logic.level.Game;

public class FlightSpell extends Ability {

	private Drawer drawer = new TileDrawer(0, 21, 7);
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
		caster.setAcceleration(caster.getAcceleration().divide(2));
	}

	@Override
	protected void onEnd(Game game, Entity caster) {
		caster.removeEffect("flying");
		caster.setAcceleration(caster.getAcceleration().multiply(2));
	}

	@Override
	protected void onUpdate(Game game, Entity caster) {

	}

}
