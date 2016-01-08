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
import rpg.element.entity.Entity;
import rpg.logic.level.Level;

public class IceBlockSpell extends Spell {

	private static Image image;
	private static int width, height;

	static {
		try {
			image = ImageIO.read(new File("img/haste.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		width = 32;
		height = 32;
	}

	private double speed;

	public IceBlockSpell() {
		super(2, 2);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, 0, 0, width, height, null);
	}

	@Override
	public void onStart(Level level, Entity caster) {
		speed = caster.getDouble("speed", 0);
		caster.set("speed", speed + 0.5 * caster.getTotalNumber("speed"));
	}

	@Override
	public void onEnd(Level level, Entity caster) {
		caster.set("speed", speed);
	}

	@Override
	public List<Requirement> getRequirements() {
		return Arrays.asList(Requirement.atLeast("mana", 1), Entity::isAlive);
	}

	@Override
	public List<Cost> getCosts() {
		return Arrays.asList(Cost.bar("mana", 1));
	}

}
