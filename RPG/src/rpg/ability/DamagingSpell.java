package rpg.ability;

import rpg.element.Entity;
import rpg.logic.level.Level;

public abstract class DamagingSpell extends EnemyAbility {

	private double mana;

	public DamagingSpell(double maxCooldown, double mana) {
		super(maxCooldown);
		this.mana = mana;
	}

	@Override
	protected boolean isCastable(Entity caster, Entity element) {
		return caster.getMana() >= mana;
	}

	@Override
	protected void onCast(Level level, Entity caster, Entity entity) {
		caster.subtractMana(mana);
		afterCast(level, caster, entity);
	}

	protected abstract void afterCast(Level level, Entity caster, Entity entity);

}