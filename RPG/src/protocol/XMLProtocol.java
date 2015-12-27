package protocol;

import rpg.Thing;
import rpg.element.Element;
import rpg.item.Inventory;

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
		}
		if (thing instanceof Inventory) {
			Inventory inventory = (Inventory) thing;
			for (int i = 0; i < inventory.getSize(); i++) {
				root.addChild(encode(inventory.get(i)));
			}
		}
		if (thing instanceof Element) {
			XMLNode loc = new XMLNode("Vector2D");
			loc.addAttribute("name", "location");
			loc.addAttribute("value", ((Element) thing).getLocation().toString());
			root.addChild(loc);

		}
		return root;
	}

}
