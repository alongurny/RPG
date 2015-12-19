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
import rpg.logic.Level;

public class HasteSpell extends Spell {

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

	public HasteSpell() {
		super(2);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, 0, 0, width, height, null);
	}

	@Override
	public void onCast(Level level, Entity caster) {
	}

	@Override
	public List<Requirement> getRequirements() {
		return new ArrayList<>();
	}

}
