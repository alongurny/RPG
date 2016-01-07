package rpg.element.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import rpg.ability.FireballSpell;
import rpg.element.Element;
import rpg.element.HealthPotion;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.logic.level.Level;

public class Dragon extends Entity {

	private static Image image;
	private static int width, height;

	static {
		try {
			image = new ImageIcon(new File("img/dragon.png").toURI().toURL()).getImage();
			width = image.getWidth(null) / 2;
			height = image.getHeight(null) / 2;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Dragon(Vector2D location) {
		super(location, Race.DRAGON);
		getAbilityHandler().addAbility(new FireballSpell(192));
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
	public void act(Level level, double dt) {
		for (Element d : level.getDynamicElements()) {
			if (d instanceof Player) {
				set("direction", d.getLocation().subtract(getLocation()).getUnitalVector());
				getAbilityHandler().tryCast(level, this, 0);
			}
		}
	}

	@Override
	public void onDeath(Level level) {
		level.addDynamicElement(new HealthPotion(getLocation()));
		level.removeDynamicElement(this);
		level.finish();
	}

}
