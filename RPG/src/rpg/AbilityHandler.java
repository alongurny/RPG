package rpg;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import rpg.element.Entity;
import rpg.level.Level;

public class AbilityHandler extends Mechanism {

	private List<Pair<Ability, Double>> abilities;
	private Entity caster;

	public AbilityHandler(Entity caster) {
		this.caster = caster;
		abilities = new CopyOnWriteArrayList<>();
	}

	public void update(Level level) {
		abilities.forEach(p -> {
			if (p.getSecond() < p.getFirst().getCooldown()) {
				put(p.getFirst(), p.getSecond() + 30e-3);
			}
		});
	}

	public void addAbility(Ability ability) {
		put(ability, ability.getCooldown());
	}

	public Ability getAbility(int i) {
		return abilities.get(i).getFirst();
	}

	public List<Pair<Ability, Double>> getAbilities() {
		return new ArrayList<>(abilities);
	}

	public void put(Ability ability, double cooldown) {
		for (int i = 0; i < abilities.size(); i++) {
			if (ability == abilities.get(i).getFirst()) {
				abilities.set(i, new Pair<>(ability, cooldown));
				return;
			}
		}
		abilities.add(new Pair<>(ability, cooldown));
	}

	public boolean isCastable(Ability ability) {
		for (Pair<Ability, Double> pair : abilities) {
			if (ability == pair.getFirst()) {
				boolean noCooldown = pair.getSecond() >= ability.getCooldown();
				if (noCooldown) {
					for (Pair<String, Double> r : ability.getRequirements()) {
						if (!caster.isRequireable(r.getFirst(), r.getSecond())) {
							return false;
						}
					}
					return true;
				}
			}
		}
		return false;
	}

	public boolean tryCast(Level level, Ability ability) {
		if (isCastable(ability)) {
			ability.onCast(level, caster);
			put(ability, 0.0);
			return true;
		}
		return false;
	}

	public boolean tryCast(Level level, int i) {
		return tryCast(level, getAbility(i));
	}

}
