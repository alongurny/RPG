package rpg.ability;

import java.util.Optional;

import rpg.element.Element;
import rpg.element.Entity;
import rpg.logic.level.Game;

public abstract class SelfAbility extends Ability {

	public SelfAbility(double maxCooldown) {
		super(maxCooldown, TargetType.SELF);
	}

	protected abstract boolean isCastable(Entity caster);

	@Override
	public final boolean isCastable(Entity caster, Optional<Element> element) {
		return isCastable(caster);
	}

	protected abstract void onCast(Game game, Entity caster);

	@Override
	public void onCast(Game game, Entity caster, Optional<Element> element) {
		onCast(game, caster);
	}

}
