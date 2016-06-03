package rpg.ability;

import rpg.element.Dice;
import rpg.element.DiceSet;
import rpg.element.Entity;
import rpg.element.MagicMissile;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.TileDrawer;
import rpg.logic.level.Level;

public class MagicMissileSpell extends DamagingSpell {

	private Drawer drawer;
	private double speed;
	private DiceSet dice;

	public MagicMissileSpell() {
		super(2, 5);
		this.speed = 64;
		drawer = TileDrawer.sprite(0, 6, 34, 36);
		dice = DiceSet.repeat(3, Dice.get(4));
	}

	@Override
	public void afterCast(Level level, Entity caster, Entity entity) {
		Vector2D location = caster.getLocation();
		level.addDynamicElement(new MagicMissile(caster, location, speed, entity, () -> (double) dice.roll()));
	}

	@Override
	public Drawer getSelfDrawer() {
		return drawer;
	}
}
