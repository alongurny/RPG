package rpg.ability;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import rpg.Mechanism;
import rpg.Requirement;
import rpg.element.entity.Entity;
import rpg.logic.level.Level;

public class AbilityHandler extends Mechanism {

	private List<Ability> abilities;

	public AbilityHandler() {
		abilities = new CopyOnWriteArrayList<>();
	}

	public void update(Level level, double dt) {
		abilities.forEach(p -> p.update(level, dt));
	}

	public void addAbility(Ability ability) {
		abilities.add(ability);
	}

	public Ability getAbility(int i) {
		return abilities.get(i);
	}

	public List<Ability> getAbilities() {
		return new ArrayList<>(abilities);
	}

	public boolean makeCastable(Ability ability, Entity caster) {
		boolean noCooldown = ability.getCooldown() == 0;
		if (noCooldown) {
			for (Requirement r : ability.getRequirements()) {
				if (!r.isRequireable(caster)) {
					return false;
				}
			}
			for (Requirement r : ability.getRequirements()) {
				r.require(caster);
			}
			return true;
		}

		return false;
	}

	public boolean isCastable(Ability ability, Entity caster) {
		boolean noCooldown = ability.getCooldown() == 0;
		if (noCooldown) {
			for (Requirement r : ability.getRequirements()) {
				if (!r.isRequireable(caster)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public boolean tryCast(Level level, Entity caster, Ability ability) {
		if (makeCastable(ability, caster)) {
			ability.onCast(level, caster);
			ability.setCooldown(ability.getNumber("maxCooldown"));
			return true;
		}
		return false;
	}

	public int getSize() {
		return abilities.size();
	}

	public boolean tryCast(Level level, Entity caster, int i) {
		return tryCast(level, caster, getAbility(i));
	}

}
