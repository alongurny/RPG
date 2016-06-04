package rpg.ability;

import java.util.Optional;

import rpg.element.Element;
import rpg.element.Entity;
import rpg.logic.level.Game;

public abstract class DurationAbility extends Ability {

	private double duration;

	public DurationAbility(double maxCooldown, double duration, TargetType targetType) {
		super(maxCooldown, targetType);
		this.duration = duration;
	}

	public DurationAbility(double maxCooldown, TargetType targetType) {
		this(maxCooldown, 0, targetType);
	}

	@Override
	public void onCast(Game game, Entity caster, Optional<Element> element) {
		onStart(game, caster, element);
		game.addTimer(duration, () -> onEnd(game, caster, element));
	}

	public abstract void onEnd(Game game, Entity caster, Optional<Element> element);

	public abstract void onStart(Game game, Entity caster, Optional<Element> element);
}
