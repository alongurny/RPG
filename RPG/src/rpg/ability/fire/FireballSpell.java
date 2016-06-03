package rpg.ability.fire;

import rpg.ability.EnemySpell;
import rpg.element.Dice;
import rpg.element.DiceSet;
import rpg.element.Entity;
import rpg.element.Fireball;
import rpg.geometry.Vector2D;
import rpg.graphics.DrawIcon;
import rpg.graphics.Drawer;
import rpg.logic.level.Level;

public class FireballSpell extends EnemySpell {

	private Drawer drawer;
	private double speed;
	private DiceSet dice;

	public FireballSpell() {
		super(2, 5);
		this.speed = 192;
		drawer = new DrawIcon("img/fireball.gif", 32, 32);
		dice = DiceSet.repeat(3, Dice.get(6));
	}

	@Override
	public void afterCast(Level level, Entity caster, Entity entity) {
		Vector2D location = caster.getLocation();
		Vector2D direction = entity.getLocation().subtract(location).getUnitalVector();
		level.addDynamicElement(new Fireball(caster, location, direction.multiply(speed), () -> (double) dice.roll()));
	}

	@Override
	public Drawer getSelfDrawer() {
		return drawer;
	}
}