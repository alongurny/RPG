package rpg.element;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import rpg.ability.FireballSpell;
import rpg.element.entity.AttributeSet;
import rpg.element.entity.Bar;
import rpg.element.entity.Entity;
import rpg.element.entity.Player;
import rpg.element.entity.Race;
import rpg.level.Level;
import rpg.physics.Vector2D;

public class Dragon extends Entity {

	private static Image image;
	private static int width, height;

	static {
		try {
			image = new ImageIcon(new File("img/dragon.png").toURI().toURL()).getImage();
			width = image.getWidth(null);
			height = image.getHeight(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Dragon(Vector2D location, AttributeSet basicAttributes) {
		super(location, basicAttributes, Race.DRAGON);
		getAbilityHandler().addAbility(new FireballSpell(10));
		putBar("mana", new Bar(100));
	}

	@Override
	protected void drawEntity(Graphics g) {
		g.drawImage(image, -width / 2, -height / 2, width, height, null);
	}

	@Override
	public Rectangle getRelativeRect() {
		return new Rectangle(-width / 2, -height / 2, width, height);
	}

	@Override
	public int getIndex() {
		return 3;
	}

	@Override
	public void onCollision(Level level, Element other) {

	}

	@Override
	public boolean isPassable(Level level, Element other) {
		if (other instanceof Entity) {
			return false;
		}
		return true;
	}

	@Override
	public void act(Level level) {
		for (Element d : level.getDynamicElements()) {
			if (d instanceof Player) {
				setDirection(d.getLocation().subtract(getLocation()));
				getAbilityHandler().tryCast(level, 0);
			}
		}
	}

	@Override
	public void onDeath(Level level) {
		level.addElement(new HealthPotion(getLocation()));
		level.removeElement(this);
		level.finish();
	}

}
