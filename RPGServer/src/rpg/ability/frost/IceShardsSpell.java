package rpg.ability.frost;

import external.Messages;
import rpg.ability.EntityTargetAbility;
import rpg.element.Element;
import rpg.element.ability.IceShard;
import rpg.element.entity.Entity;
import rpg.geometry.Vector2D;
import rpg.graphics.Drawer;
import rpg.logic.level.Game;

public class IceShardsSpell extends EntityTargetAbility {

	private double speed;
	private Drawer drawer;

	public IceShardsSpell() {
		super(12, 5);
		this.speed = 192;
		drawer = Messages.getTileDrawer("IceShard");
	}

	@Override
	public Drawer getSelfDrawer() {
		return drawer;
	}

	@Override
	public void onCast(Game game, Entity caster, Entity target) {
		Vector2D location = caster.getLocation().add(new Vector2D(0, -caster.getRelativeRect().getHeight() / 2));
		double phase = target.getLocation().subtract(location).getPhase();
		for (int i = -1; i <= 1; i++) {
			game.addDynamicElement(new IceShard(caster, location,
					Vector2D.fromPolar(speed, phase + i * Math.toRadians(30)), game.getDefaultGravity(), () -> 4.0));
		}
	}

	@Override
	protected boolean isActive(Game game, Entity caster, Entity target) {
		return false;
	}

	@Override
	protected boolean isCastable(Entity caster, Entity target) {
		return Element.distance(caster, target) <= 360 && !caster.isFriendly(target);
	}

	@Override
	protected void onEnd(Game game, Entity caster, Entity target) {

	}

	@Override
	protected void onUpdate(Game game, Entity caster, Entity target) {

	}
}
