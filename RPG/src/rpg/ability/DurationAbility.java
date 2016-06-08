package rpg.ability;

import rpg.element.Entity;
import rpg.logic.level.Game;

public abstract class DurationAbility extends Ability {

	private double duration;
	private boolean expired;

	public DurationAbility(double maxCooldown, double mana, double duration) {
		super(maxCooldown, mana);
		this.duration = duration;
	}

	@Override
	public void onCast(Game game, Entity caster) {
		expired = false;
		game.addTimer(duration, () -> expired = true);
		onStart(game, caster);
	}

	@Override
	protected boolean isActive(Game game, Entity caster) {
		return !expired;
	}

	protected abstract void onStart(Game game, Entity caster);
}
