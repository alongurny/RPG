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
import rpg.level.Level;
import rpg.physics.Vector2D;

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

	private double distance;
	private double speed;

	public RocketSpell(double speed) {
		super(2);
		distance = 32;
		this.speed = speed;
	}

	@Override
	public void onCast(Level level, Entity caster) {
		Vector2D casterLocation = caster.getLocation();
		Vector2D casterDirection = caster.getDirection();
		Vector2D fireballLocation = casterLocation.add(casterDirection.multiply(distance));
		level.addElement(new Rocket(fireballLocation, casterDirection, speed));
	}

	@Override
	public List<Requirement> getRequirements() {
		return Arrays.asList(new BarRequirement("mana", 0.2));

	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, 0, 0, width, height, null);
	}

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

}
