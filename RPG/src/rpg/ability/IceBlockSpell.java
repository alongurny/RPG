package rpg.ability;

import java.util.Arrays;
import java.util.List;

import rpg.Cost;
import rpg.Requirement;
import rpg.element.Entity;
import rpg.element.IceBlock;
import rpg.geometry.Rectangle;
import rpg.graphics.draw.Drawer;
import rpg.logic.level.Level;

public class IceBlockSpell extends Spell {

	private IceBlock block;

	public IceBlockSpell() {
		super(2, 2);
	}

	@Override
	public void onStart(Level level, Entity caster) {
		Entity target = (Entity) caster.getTarget();
		Rectangle rect = target.getAbsoluteRect();
		block = new IceBlock(target.getLocation(), Math.max(rect.getWidth(), rect.getHeight()) * 1.5);
		level.addDynamicElement(block);
	}

	@Override
	public void onEnd(Level level, Entity caster) {
		level.removeDynamicElement(block);
	}

	@Override
	public List<Requirement> getRequirements() {
		return Arrays.asList(Requirement.atLeast("mana", 1), Entity::isAlive,
				entity -> entity.hasTarget() && entity.getTarget() instanceof Entity);
	}

	@Override
	public List<Cost> getCosts() {
		return Arrays.asList(Cost.bar("mana", 1));
	}

	@Override
	public Drawer getSelfDrawer() {
		return IceBlock.sprite;
	}

}
