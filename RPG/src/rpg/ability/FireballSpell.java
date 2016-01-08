package rpg.ability;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import rpg.Requirement;
import rpg.Requirement.AliveRequirement;
import rpg.Requirement.BarRequirement;
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
	public void onCast(Level level, Entity caster) {
		Vector2D casterLocation = caster.getLocation();
		Vector2D casterDirection = caster.getVector("direction");
		Vector2D fireballLocation = casterLocation;
		level.addDynamicElement(new Fireball(caster, fireballLocation, casterDirection, getDouble("speed")));
	}

	@Override
	public List<Requirement> getRequirements() {
		return Arrays.asList(new BarRequirement("mana", 1.0), new AliveRequirement());

	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, 0, 0, width, height, null);
	}

}
