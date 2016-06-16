package rpg.ability.physical;

import external.Messages;
import rpg.ability.EntityTargetAbility;
import rpg.ability.damage.DamageType;
import rpg.element.Element;
import rpg.element.entity.Attribute;
import rpg.element.entity.Entity;
import rpg.graphics.Drawer;
import rpg.logic.level.Game;

public class BasicAttack extends EntityTargetAbility {

	private static final Drawer drawer = Messages.getTileDrawer("BasicAttack");

	public BasicAttack() {
		super(1, 0);
	}

	@Override
	public Drawer getSelfDrawer() {
		return drawer;
	}

	@Override
	public void onCast(Game game, Entity caster, Entity entity) {
		entity.damage(0.35 * caster.getAttribute(Attribute.STR), DamageType.PHYSICAL);
	}

	@Override
	protected boolean isCastable(Entity caster, Entity target) {
		return !caster.isFriendly(target) && Element.distance(caster, target) <= caster.getRelativeRect().getWidth()
				* 0.2 * caster.getAttribute(Attribute.STR);
	}
}
