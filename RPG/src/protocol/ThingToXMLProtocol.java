package protocol;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import rpg.Attribute;
import rpg.AttributeSet;
import rpg.Thing;
import rpg.ability.Ability;
import rpg.element.Element;
import rpg.element.Entity;
import rpg.exception.RPGException;
import rpg.geometry.Vector2D;
import rpg.item.Item;

public class ThingToXMLProtocol implements Protocol<Thing, Node> {

	private DocumentBuilder builder;

	public ThingToXMLProtocol() {
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Thing decode(Node root) {
		org.w3c.dom.Element element = (org.w3c.dom.Element) root;
		NodeList elements = element.getChildNodes();
		AttributeSet set = new AttributeSet();
		List<Ability> abilities = new ArrayList<>();
		List<Item> items = new ArrayList<>();
		for (int i = 0; i < elements.getLength(); i++) {
			org.w3c.dom.Element node = (org.w3c.dom.Element) elements.item(i);
			String key = node.getAttribute("key");
			String nodeValue = node.getAttribute("value");
			switch (node.getTagName()) {
			case "double":
				set.set(key, Double.valueOf(nodeValue));
				break;
			case "integer":
				set.set(key, Integer.valueOf(nodeValue));
				break;
			case "boolean":
				set.set(key, Boolean.valueOf(nodeValue));
				break;
			case "vector":
				set.set(key, Vector2D.valueOf(nodeValue));
				break;
			case "string":
				set.set(key, String.valueOf(nodeValue));
				break;

			default:
				try {
					Class<?> cls = Class.forName(node.getTagName());
					if (Ability.class.isAssignableFrom(cls)) {
						abilities.add((Ability) decode(node));
					}
					if (Item.class.isAssignableFrom(cls)) {
						items.add((Item) decode(node));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
		try {
			Thing thing = Attribute.getThing(Class.forName(element.getTagName()), set);
			for (String key : set.getKeys()) {
				thing.set(key, set.get(key));
			}
			switch (element.getTagName()) {
			case "rpg.element.Player":
			case "rpg.element.Dragon":
				Entity entity = (Entity) thing;
				abilities.forEach(a -> entity.addAbility(a));
				items.forEach(i -> entity.getInventory().add(i));
				break;
			}
			return thing;
		} catch (ClassNotFoundException ex) {
			throw new RPGException(ex);
		}
	}

	@Override
	public Node encode(Thing thing) {
		return encode0(thing, builder.newDocument());
	}

	public Node encode0(Thing thing, Document document) {
		org.w3c.dom.Element root = document.createElement(thing.getClass().getName());
		for (String key : thing.getKeys()) {
			org.w3c.dom.Element node = document.createElement(thing.getType(key).getName());
			root.appendChild(node);
			node.setAttribute("key", key);
			node.setAttribute("value", thing.get(key).toString());
		}
		if (thing instanceof Ability || thing instanceof Item) {
		} else if (thing instanceof Element) {
			if (thing instanceof Entity) {
				Entity entity = (Entity) thing;
				for (Ability a : entity.getAbilities()) {
					root.appendChild(encode0(a, document));
				}
				for (Item i : entity.getInventory()) {
					root.appendChild(encode0(i, document));
				}
			}
		} else {
			throw new RPGException("No match for " + thing.getClass());
		}
		return root;

	}
}
