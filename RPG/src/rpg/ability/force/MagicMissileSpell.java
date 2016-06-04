package rpg.ability.force;

import rpg.ability.EnemySpell;
import rpg.element.Dice;
import rpg.element.DiceSet;
import rpg.element.Entity;
import rpg.element.MagicMissile;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.TileDrawer;
import rpg.logic.level.Game;

public class MagicMissileSpell extends EnemySpell {

	private Drawer drawer;
	private double speed;
	private DiceSet dice;

	public MagicMissileSpell() {
		super(2, 5);
		this.speed = 96;
		drawer = TileDrawer.sprite(0, 6, 34, 36);
		dice = DiceSet.repeat(3, Dice.get(4));
	}

	@Override
	public void afterCast(Game game, Entity caster, Entity entity) {
		Vector2D location = caster.getLocation();
		game.addDynamicElement(new MagicMissile(caster, location, speed, entity, () -> (double) dice.roll()));
	}

	@Override
	public Drawer getSelfDrawer() {
		return drawer;
	}
}
