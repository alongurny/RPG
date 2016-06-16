package rpg.item;

import external.Messages;
import rpg.graphics.Drawer;

public class MasterKey extends Item {

	private Drawer drawer = Messages.getTileDrawer("MasterKey");

	public MasterKey() {
	}

	@Override
	public Drawer getDrawer() {
		return drawer;
	}

}
