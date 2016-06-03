package rpg.ability;

import java.util.Optional;

import rpg.element.Element;
import rpg.element.Entity;
import rpg.element.Fireball;
import rpg.geometry.Vector2D;
import rpg.graphics.DrawIcon;
import rpg.graphics.Drawer;
import rpg.logic.level.Level;

public class FireballSpell extends Ability {

	private Drawer drawer;
	private double speed;

	public FireballSpell() {
		super(2, TargetType.ENEMY);
		this.speed = 192;
		drawer = new DrawIcon("img/fireball.gif", 32, 32);
	}

	public boolean isCastable(Entity caster, Optional<Element> element) {
		return caster.isAlive() && element.isPresent() && element.get() instanceof Entity && caster.getMana() >= 1
				&& element.get() != caster;
	}

	@Override
	public void onCast(Level level, Entity caster, Optional<Element> element) {
		caster.subtractMana(1);
		Vector2D location = caster.getLocation();
		Vector2D direction = element.get().getLocation().subtract(location).getUnitalVector();
		level.addDynamicElement(new Fireball(caster, location, direction.multiply(speed)));
	}

	@Override
	public Drawer getSelfDrawer() {
		return drawer;
	}
}
