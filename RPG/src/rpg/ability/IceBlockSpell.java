package rpg.ability;

import rpg.element.Element;
import rpg.element.Entity;
import rpg.element.IceBlock;
import rpg.geometry.Rectangle;
import rpg.graphics.Drawer;
import rpg.graphics.TileDrawer;
import rpg.logic.level.Level;

public class IceBlockSpell extends DurationAbility {

	private IceBlock block;
	private Drawer drawer;

	public IceBlockSpell() {
		super(2, 2);
		drawer = new TileDrawer(0, 16, 27);
	}

	@Override
	public boolean isCastable(Entity caster, Element... elements) {
		return caster.isAlive() && caster.getMana() >= 1 && elements.length == 1 && elements[0] instanceof Entity;
	}

	@Override
	public void onStart(Level level, Entity caster, Element... elements) {
		caster.subtractMana(1);
		Entity target = (Entity) elements[0];
		Rectangle rect = target.getAbsoluteRect();
		block = new IceBlock(target.getLocation(), Math.max(rect.getWidth(), rect.getHeight()) * 1.5);
		level.addDynamicElement(block);
	}

	@Override
	public void onEnd(Level level, Entity caster, Element... elements) {
		level.removeDynamicElement(block);
	}

	@Override
	public Drawer getSelfDrawer() {
		return drawer;
	}

}
