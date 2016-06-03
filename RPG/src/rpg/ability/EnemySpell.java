package rpg.ability;

import rpg.element.Entity;
import rpg.logic.level.Level;

public abstract class EnemySpell extends EnemyAbility {

	private double mana;

	public EnemySpell(double maxCooldown, double mana) {
		super(maxCooldown);
		this.mana = mana;
	}

	protected abstract void afterCast(Level level, Entity caster, Entity entity);

	@Override
	protected boolean isCastable(Entity caster, Entity element) {
		return caster.getMana() >= mana;
	}

	@Override
	protected void onCast(Level level, Entity caster, Entity entity) {
		caster.subtractMana(mana);
		afterCast(level, caster, entity);
	}

}