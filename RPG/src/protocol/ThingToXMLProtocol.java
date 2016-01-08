package protocol;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import rpg.Attribute;
import rpg.AttributeSet;
import rpg.Thing;
import rpg.ability.Ability;
import rpg.ability.FireballSpell;
import rpg.ability.HasteSpell;
import rpg.element.Bonus;
import rpg.element.Fireball;
import rpg.element.Portal;
import rpg.element.entity.Entity;
import rpg.exception.RPGException;
import rpg.geometry.Vector2D;
import rpg.item.Inventory;
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
		Element element = (Element) root;
		NodeList elements = element.getChildNodes();
		AttributeSet set = new AttributeSet();
		List<Ability> abilities = new ArrayList<>();
		for (int i = 0; i < elements.getLength(); i++) {
			Element node = (Element) elements.item(i);
			String key = node.getAttribute("key");
			String nodeValue = node.getAttribute("value");
			switch (node.getTagName()) {
			case "double":
				set.set(key, Double.valueOf(nodeValue));
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
			case "rpg.ability.FireballSpell":
				abilities.add((FireballSpell) decode(node));
				break;
			case "rpg.ability.HasteSpell":
				abilities.add((HasteSpell) decode(node));
				break;
			}
		}
		try {
			Thing thing = Attribute.getThing(Class.forName(element.getTagName()), set);
			for (String key : set.getKeys()) {
				thing.set(key, set.get(key));
			}
			switch (element.getTagName()) {
			case "rpg.element.entity.Player":
			case "rpg.element.entity.Dragon":
				Entity entity = (Entity) thing;
				abilities.forEach(a -> entity.addAbility(a));
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
		if (thing instanceof Ability || thing instanceof Item || thing instanceof Bonus || thing instanceof Portal
				|| thing instanceof Fireball) {
		} else if (thing instanceof Entity) {
			Entity entity = (Entity) thing;
			root.appendChild(encode0(entity.getInventory(), document));
			for (Ability a : entity.getAbilities()) {
				root.appendChild(encode0(a, document));
			}

		} else if (thing instanceof Inventory) {
			Inventory inventory = (Inventory) thing;
			for (int i = 0; i < inventory.getSize(); i++) {
				root.appendChild(encode0(inventory.get(i), document));
			}
		} else {
			throw new RPGException("No match for " + thing.getClass());
		}
		return root;
	}
}
