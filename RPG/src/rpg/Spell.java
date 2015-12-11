package rpg;

import rpg.element.Entity;

public abstract class Spell extends Ability {
	private Entity caster;

	public Spell(Entity caster) {
		this.caster = caster;
	}
}
