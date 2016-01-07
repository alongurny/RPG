package protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import rpg.Attribute;
import rpg.Thing;
import rpg.ability.Ability;
import rpg.ability.AbilityHandler;
import rpg.ability.FireballSpell;
import rpg.ability.HasteSpell;
import rpg.ability.RocketSpell;
import rpg.element.Bonus;
import rpg.element.Fireball;
import rpg.element.Portal;
import rpg.element.Rocket;
import rpg.element.entity.Bar;
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
	public Thing decode(Node d) {
		Element e = (Element) d;

		NodeList elements = e.getChildNodes();
		Thing thing = new Thing() {
		};
		Map<String, Bar> bars = new HashMap<>();
		List<Ability> abilities = new ArrayList<>();
		AbilityHandler abilityHandler = null;
		for (int i = 0; i < elements.getLength(); i++) {
			Element node = (Element) elements.item(i);
			String key = node.getAttribute("key");
			String nodeValue = node.getAttribute("value");
			switch (node.getTagName()) {
			case "Double":
				thing.set(key, Double.valueOf(nodeValue));
				break;
			case "Boolean":
				thing.set(key, Boolean.valueOf(nodeValue));
				break;
			case "Vector2D":
				thing.set(key, Vector2D.valueOf(nodeValue));
				break;
			case "String":
				thing.set(key, nodeValue);
				break;
			case "Bar":
				bars.put(key, new Bar(Double.parseDouble(nodeValue), Double.parseDouble(node.getAttribute("maximum"))));
				break;
			case "rpg.ability.AbilityHandler":
				abilityHandler = (AbilityHandler) decode(node);
				break;
			case "rpg.ability.FireballSpell":
				abilities.add((FireballSpell) decode(node));
				break;
			case "rpg.ability.HasteSpell":
				abilities.add((HasteSpell) decode(node));
				break;
			case "rpg.ability.RocketSpell":
				abilities.add((RocketSpell) decode(node));
				break;
			}
		}
		Thing element;
		try {
			element = Attribute.getThing(Class.forName(e.getTagName()), thing);
			for (String key : thing.getKeys()) {
				element.set(key, thing.get(key));
			}
			switch (e.getTagName()) {
			case "rpg.element.entity.Player":
			case "rpg.element.entity.Dragon":
				Entity entity = (Entity) element;
				for (Entry<String, Bar> entry : bars.entrySet()) {
					entity.putBar(entry.getKey(), entry.getValue());
				}
				if (abilityHandler != null) {
					abilityHandler.getAbilities().forEach(a -> entity.getAbilityHandler().addAbility(a));
				}
				break;
			case "rpg.ability.AbilityHandler":
				AbilityHandler ah = (AbilityHandler) element;
				abilities.forEach(a -> ah.addAbility(a));
				break;
			}
			return element;
		} catch (ClassNotFoundException e1) {
			throw new RPGException(e1);
		}
	}

	@Override
	public Node encode(Thing thing) {
		return encode0(thing, builder.newDocument());
	}

	public Node encode0(Thing thing, Document document) {
		org.w3c.dom.Element root = document.createElement(thing.getClass().getName());
		for (String key : thing.getKeys()) {
			org.w3c.dom.Element node = document.createElement(thing.getType(key).getSimpleName());
			root.appendChild(node);
			node.setAttribute("key", key);
			node.setAttribute("value", thing.get(key).toString());
		}
		if (thing instanceof Ability || thing instanceof Item || thing instanceof Bonus || thing instanceof Portal
				|| thing instanceof Fireball || thing instanceof Rocket) {

		} else if (thing instanceof AbilityHandler) {
			AbilityHandler ah = (AbilityHandler) thing;
			for (Ability a : ah.getAbilities()) {
				root.appendChild(encode0(a, document));
			}
		} else if (thing instanceof Entity) {
			Entity entity = (Entity) thing;
			root.appendChild(encode0(entity.getInventory(), document));
			root.appendChild(encode0(entity.getAbilityHandler(), document));
			Map<String, Bar> bars = entity.getBars();
			for (Entry<String, Bar> entry : bars.entrySet()) {
				org.w3c.dom.Element entryNode = document.createElement("Bar");
				entryNode.setAttribute("key", entry.getKey());
				entryNode.setAttribute("value", "" + entry.getValue().getValue());
				entryNode.setAttribute("maximum", "" + entry.getValue().getMaximum());
				root.appendChild(entryNode);
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
