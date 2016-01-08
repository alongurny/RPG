package rpg.ability;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import rpg.Cost;
import rpg.Requirement;
import rpg.element.Fireball;
import rpg.element.entity.Entity;
import rpg.geometry.Vector2D;
import rpg.logic.level.Level;

public class FireballSpell extends Spell {

	private static Image image;
	private static int width, height;

	static {
		try {
			image = ImageIO.read(new File("img/fireball.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		width = 32;
		height = 32;
	}

	public FireballSpell(double speed) {
		super(2);
		set("speed", speed);
	}

	@Override
	public List<Requirement> getRequirements() {
		return Arrays.asList(Requirement.atLeast("mana", 1.0), Entity::isAlive,
				entity -> entity.hasTarget() && entity.getTarget().getInteger("id") != entity.getInteger("id")
						&& entity.getTarget() instanceof Entity);
	}

	@Override
	public List<Cost> getCosts() {
		return Arrays.asList(Cost.bar("mana", 1));
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, 0, 0, width, height, null);
	}

	@Override
	public void onStart(Level level, Entity caster) {
		Vector2D location = caster.getLocation();
		Vector2D direction = caster.getTarget().getLocation().subtract(location).getUnitalVector();
		level.addDynamicElement(new Fireball(caster, location, direction, getDouble("speed")));
	}

	@Override
	public void onEnd(Level level, Entity caster) {

	}

}
