package protocol;

import rpg.element.Element;
import rpg.item.Inventory;

public class XMLProtocol implements Protocol<Object, XMLNode> {

	@Override
	public Object decode(XMLNode d) {
		return null;
	}

	@Override
	public XMLNode encode(Object e) {
		XMLNode root = new XMLNode(e.getClass().getSimpleName());
		if (e instanceof Inventory) {
			Inventory inventory = (Inventory) e;
			for (int i = 0; i < inventory.getSize(); i++) {
				root.addChild(encode(inventory.get(i)));
			}
		} else if (e instanceof Element) {
			XMLNode loc = new XMLNode("Vector2D");
			loc.addAttribute("name", "location");
			loc.addAttribute("value", ((Element) e).getLocation().toString());
			loc.addAttribute("name", "basicAttributes");
			root.addChild(loc);
			XMLNode attr = new XMLNode("AttributeSet");
			attr.addAttribute("name", "basicAttributes");
			root.addChild(attr);
		}
		return root;
	}

}
