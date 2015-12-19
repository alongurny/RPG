package rpg.ability;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import rpg.Requirement;
import rpg.element.entity.Entity;
import rpg.element.entity.Player;
import rpg.logic.Level;

public class HasteSpell extends DurationSpell {

	private static Image image;
	private static int width, height;
	private double speed;

	static {
		try {
			image = ImageIO.read(new File("img/haste.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		width = 32;
		height = 32;
	}

	public HasteSpell() {
		super(2, 2);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, 0, 0, width, height, null);
	}

	@Override
	public void onStart(Level level, Entity caster) {
		if (caster instanceof Player) {
			Player p = (Player) caster;
			double speed = p.getSpeed();
			p.setSpeed(2.5 * speed);
		}
	}

	@Override
	public void onEnd(Level level, Entity caster) {
		if (caster instanceof Player) {
			Player p = (Player) caster;
			level.addTimer(2, () -> p.setSpeed(speed));
		}
	}

	@Override
	public List<Requirement> getRequirements() {
		return new ArrayList<>();
	}

}
