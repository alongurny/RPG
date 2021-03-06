package rpg.ability.frost;

import external.Messages;
import rpg.ability.EntityTargetAbility;
import rpg.element.Element;
import rpg.element.ability.IceBlock;
import rpg.element.entity.Entity;
import rpg.geometry.Rectangle;
import rpg.graphics.Drawer;
import rpg.logic.level.Game;

public class IceBlockSpell extends EntityTargetAbility {

	private IceBlock block;
	private Drawer drawer;
	private double duration;
	private boolean active;

	public IceBlockSpell() {
		super(10, 10);
		drawer = Messages.getTileDrawer("IceBlock");
		this.duration = 5;
	}

	@Override
	public Drawer getSelfDrawer() {
		return drawer;
	}

	@Override
	public boolean isCastable(Entity caster, Entity target) {
		return Element.distance(caster, target) <= 180 && caster.isAlive();
	}

	@Override
	public void onCast(Game game, Entity caster, Entity target) {
		active = true;
		Rectangle rect = target.getAbsoluteRect();
		block = new IceBlock(target.getLocation(), Math.max(rect.getWidth(), rect.getHeight()) * 1.5);
		game.addDynamicElement(block);
		game.addTimer(duration, () -> active = false);
	}

	@Override
	public void onEnd(Game game, Entity caster, Entity target) {
		game.removeDynamicElement(block);
	}

	@Override
	protected boolean isActive(Game game, Entity caster, Entity target) {
		return active;
	}

}
