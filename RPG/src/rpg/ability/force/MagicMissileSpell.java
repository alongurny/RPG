package rpg.ability.force;

import external.Messages;
import rpg.ability.EntityTargetAbility;
import rpg.ability.damage.Dice;
import rpg.ability.damage.DiceSet;
import rpg.element.ability.MagicMissile;
import rpg.element.entity.Entity;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.graphics.TileDrawer;
import rpg.logic.level.Game;

public class MagicMissileSpell extends EntityTargetAbility {

	private static final Drawer drawer = TileDrawer.sprite(Messages.getInt("MagicMissileSpell.tileset"),
			Messages.getInt("MagicMissileSpell.row"), Messages.getInt("MagicMissileSpell.firstColumn"),
			Messages.getInt("MagicMissileSpell.lastColumn"));
	private double speed;
	private DiceSet dice;

	public MagicMissileSpell() {
		super(2, 5);
		this.speed = 96;
		dice = DiceSet.repeat(3, Dice.get(4));
	}

	@Override
	public Drawer getSelfDrawer() {
		return drawer;
	}

	@Override
	public void onCast(Game game, Entity caster, Entity entity) {
		Vector2D location = caster.getLocation();
		game.addDynamicElement(new MagicMissile(caster, location, speed, entity, () -> (double) dice.roll()));
	}

	@Override
	protected boolean isCastable(Entity caster, Entity target) {
		return !caster.isFriendly(target);
	}
}
