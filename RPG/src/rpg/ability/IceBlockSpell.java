package rpg.ability;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import rpg.Cost;
import rpg.Requirement;
import rpg.element.IceBlock;
import rpg.element.entity.DisabledEffect;
import rpg.element.entity.Entity;
import rpg.geometry.Rectangle;
import rpg.graphics.Sprite;
import rpg.graphics.Tileset;
import rpg.logic.level.Level;

public class IceBlockSpell extends Spell {

	private static BufferedImage image = Sprite.get(Tileset.get(0), 16, 27).get(0);

	private IceBlock block;

	public IceBlockSpell() {
		super(2, 2);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}

	@Override
	public void onStart(Level level, Entity caster) {
		Entity target = (Entity) caster.getTarget();
		Rectangle rect = target.getAbsoluteRect();
		block = new IceBlock(target.getLocation(), Math.max(rect.getWidth(), rect.getHeight()) * 1.5);
		level.addDynamicElement(block);
		target.addEffect(new DisabledEffect(2));
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

}
