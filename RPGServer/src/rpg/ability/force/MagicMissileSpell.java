package rpg.ability.force;

import external.Messages;
import rpg.ability.EntityTargetAbility;
import rpg.ability.damage.Dice;
import rpg.ability.damage.DiceSet;
import rpg.element.Element;
import rpg.element.ability.MagicMissile;
import rpg.element.entity.Entity;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.logic.level.Game;

public class MagicMissileSpell extends EntityTargetAbility {

	private Drawer drawer = Messages.getSprite("MagicMissile");
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
		Vector2D location = caster.getLocation().add(new Vector2D(0, -caster.getRelativeRect().getHeight() / 2));
		game.addDynamicElement(new MagicMissile(caster, location, speed, entity, () -> (double) dice.roll()));
	}

	@Override
	protected boolean isCastable(Entity caster, Entity target) {
		return Element.distance(caster, target) <= 360 && !caster.isFriendly(target);
	}
}
