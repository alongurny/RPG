package rpg.ability.fire;

import external.Messages;
import rpg.ability.EntityTargetAbility;
import rpg.ability.damage.Dice;
import rpg.ability.damage.DiceSet;
import rpg.element.ability.Fireball;
import rpg.element.entity.Entity;
import rpg.geometry.Vector2D;
import rpg.graphics.DrawIcon;
import rpg.graphics.Drawer;
import rpg.logic.level.Game;

public class FireballSpell extends EntityTargetAbility {

	private Drawer drawer;
	private double speed;
	private DiceSet dice;

	public FireballSpell() {
		super(12, 5);
		this.speed = 192;
		drawer = new DrawIcon(Messages.getString("FireballSpell.img"), 32, 32); //$NON-NLS-1$
		dice = DiceSet.repeat(3, Dice.get(6));
	}

	@Override
	public Drawer getSelfDrawer() {
		return drawer;
	}

	@Override
	public void onCast(Game game, Entity caster, Entity target) {
		Vector2D location = caster.getLocation().add(new Vector2D(0, -caster.getRelativeRect().getHeight() / 2));
		Vector2D direction = target.getLocation().subtract(location).getUnitalVector();
		game.addDynamicElement(new Fireball(caster, location, direction.multiply(speed), () -> (double) dice.roll()));
	}

	@Override
	protected boolean isActive(Game game, Entity caster, Entity target) {
		return false;
	}

	@Override
	protected boolean isCastable(Entity caster, Entity target) {
		return !caster.isFriendly(target);
	}

	@Override
	protected void onEnd(Game game, Entity caster, Entity target) {

	}

	@Override
	protected void onUpdate(Game game, Entity caster, Entity target) {

	}
}
