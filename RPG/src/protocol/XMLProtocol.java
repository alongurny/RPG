package protocol;

import rpg.Thing;
import rpg.element.entity.Entity;
import rpg.element.entity.Player;
import rpg.element.entity.Race;
import rpg.item.Inventory;
import rpg.item.MasterKey;
import rpg.physics.Vector2D;

public class XMLProtocol implements Protocol<Thing, XMLNode> {

	@Override
	public Thing decode(XMLNode d) {
		return null;
	}

	@Override
	public XMLNode encode(Thing thing) {
		XMLNode root = new XMLNode(thing.getClass().getSimpleName());
		for (String key : thing.getKeys()) {
			XMLNode node = new XMLNode("Attribute");
			node.addAttribute("key", key);
			node.addAttribute("value", thing.get(key).toString());
			root.addChild(node);
		}
		if (thing instanceof Entity) {
			Entity entity = (Entity) thing;
			root.addChild(encode(entity.getInventory()));
		}
		if (thing instanceof Inventory) {
			Inventory inventory = (Inventory) thing;
			for (int i = 0; i < inventory.getSize(); i++) {
				root.addChild(encode(inventory.get(i)));
			}

		}

		return root;
	}

	public static void main(String[] args) {
		Player player = new Player(Vector2D.NORTH, Race.HUMAN);
		player.getInventory().add(new MasterKey());
		System.out.println(new XMLProtocol().encode(player));
	}

}
