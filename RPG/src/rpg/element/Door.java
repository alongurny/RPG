package rpg.element;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import rpg.Interactive;
import rpg.element.entity.Entity;
import rpg.item.MasterKey;
import rpg.logic.Level;
import rpg.physics.Vector2D;
import rpg.ui.Rectangle;
import rpg.ui.Util;

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

	private boolean open;

	public Door(Vector2D location) {
		super(location);
		open = false;
	}

	@Override
	public void draw(Graphics g) {
		Image image = open ? openImage : closedImage;
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
		return open;
	}

	@Override
	public boolean isInteractable(Level level, Entity other) {
		for (int i = 0; i < other.getInventory().getSize(); i++) {
			if (other.getInventory().get(i) instanceof MasterKey) {
				if (Util.distance(other, this) < 36) {
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
				open = true;
				other.getInventory().removeAt(i);
				return;
			}
		}
	}

}
