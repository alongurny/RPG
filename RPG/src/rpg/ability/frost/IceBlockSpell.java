package rpg.ability.frost;

import java.util.Optional;

import rpg.ability.DurationAbility;
import rpg.ability.TargetType;
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
		super(10, 2, TargetType.ANY_ENTITY);
		drawer = new TileDrawer(0, 16, 27);
	}

	@Override
	public boolean isCastable(Entity caster, Optional<Element> element) {
		return caster.isAlive() && element.isPresent() && caster.getMana() >= 1 && element.get() instanceof Entity;
	}

	@Override
	public void onStart(Level level, Entity caster, Optional<Element> element) {
		caster.subtractMana(1);
		Entity target = (Entity) element.get();
		Rectangle rect = target.getAbsoluteRect();
		block = new IceBlock(target.getLocation(), Math.max(rect.getWidth(), rect.getHeight()) * 1.5);
		level.addDynamicElement(block);
	}

	@Override
	public void onEnd(Level level, Entity caster, Optional<Element> element) {
		level.removeDynamicElement(block);
	}

	@Override
	public Drawer getSelfDrawer() {
		return drawer;
	}

}
