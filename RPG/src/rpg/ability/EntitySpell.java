package rpg.ability;

import rpg.element.Entity;
import rpg.logic.level.Game;

public abstract class EntitySpell extends EntityAbility {

	private double mana;

	public EntitySpell(double maxCooldown, double mana) {
		super(maxCooldown);
		this.mana = mana;
	}

	protected abstract void afterCast(Game game, Entity caster, Entity entity);

	@Override
	protected boolean isCastable(Entity caster, Entity element) {
		return caster.getMana() >= mana;
	}

	@Override
	protected void onCast(Game game, Entity caster, Entity entity) {
		caster.subtractMana(mana);
		afterCast(game, caster, entity);
	}

}