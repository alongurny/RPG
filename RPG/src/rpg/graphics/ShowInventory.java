package rpg.graphics;

import java.awt.Color;

import rpg.element.Player;
import rpg.item.Item;

public class ShowInventory {

	private double x, y;
	private Player player;

	public ShowInventory(double x, double y, Player player) {
		this.x = x;
		this.y = y;
		this.player = player;
	}

	public Drawer getDrawer() {
		return new Translate(x, y).andThen(getIn()).andThen(new Translate(-x, -y));
	}

	private Drawer getIn() {
		Drawer d = new Popup(200, 200, new Color(100, 100, 100, 255), "Inventory").getDrawer();
		for (Item i : player.getInventory()) {
			d = d.andThen(new Translate(40, 40).andThen(i.getDrawer()));
		}
		d = d.andThen(new Translate(-40 * player.getInventory().size(), -40 * player.getInventory().size()));
		return d;

	}

}
