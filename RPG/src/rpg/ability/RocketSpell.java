package rpg.ability;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import rpg.Requirement;
import rpg.Requirement.BarRequirement;
import rpg.element.Rocket;
import rpg.element.entity.Entity;
import rpg.geometry.Vector2D;
import rpg.logic.level.Level;

public class RocketSpell extends Spell {

	private static Image image;
	private static int width, height;

	static {
		try {
			image = ImageIO.read(new File("img/rocket.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		width = 32;
		height = 32;
	}

	public RocketSpell(double speed) {
		super(2.0);
		set("distance", 32.0);
		set("speed", speed);
	}

	@Override
	public void onCast(Level level, Entity caster) {
		Vector2D casterLocation = caster.getLocation();
		Vector2D casterDirection = caster.getVector("direction");
		Vector2D fireballLocation = casterLocation.add(casterDirection.multiply(getDouble("distance")));
		level.addDynamicElement(new Rocket(fireballLocation, casterDirection, getDouble("speed")));
	}

	@Override
	public List<Requirement> getRequirements() {
		return Arrays.asList(new BarRequirement("mana", 0.2));

	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, 0, 0, width, height, null);
	}

}
