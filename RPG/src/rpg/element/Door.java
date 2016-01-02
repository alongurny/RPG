package rpg.element;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import rpg.Interactive;
import rpg.element.entity.Entity;
import rpg.geometry.Rectangle;
import rpg.geometry.Vector2D;
import rpg.item.MasterKey;
import rpg.logic.level.Level;

public class Door extends Element implements Interactive {

	private static Image openImage, closedImage;
	private static int width, height;

	static {
		width = 32;
		height = 32;
		try {
			openImage = ImageIO.read(new File("img/openDoor.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			closedImage = ImageIO.read(new File("img/closedDoor.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Door(Vector2D location) {
		super(location);
	}

	public boolean isOpen() {
		return getBoolean("open");
	}

	@Override
	public void draw(Graphics g) {
		Image image = isOpen() ? openImage : closedImage;
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
	public void update(Level level, double dt) {

	}

	@Override
	public void onCollision(Level level, Element other) {

	}

	@Override
	public boolean isPassable(Level level, Element other) {
		return isOpen();
	}

	@Override
	public boolean isInteractable(Level level, Entity other) {
		for (int i = 0; i < other.getInventory().getSize(); i++) {
			if (other.getInventory().get(i) instanceof MasterKey) {
				if (Element.distance(other, this) < 36) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void onInteract(Level level, Entity other) {
		for (int i = 0; i < other.getInventory().getSize(); i++) {
			if (other.getInventory().get(i) instanceof MasterKey) {
				set("open", true);
				other.getInventory().removeAt(i);
				return;
			}
		}
	}

}
