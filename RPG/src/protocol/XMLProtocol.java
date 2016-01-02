package protocol;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
import rpg.element.entity.Player;
import rpg.element.entity.Race;
import rpg.exception.RPGException;
import rpg.item.Inventory;
import rpg.item.Item;
import rpg.physics.Vector2D;

public class XMLProtocol implements Protocol<Thing, Node> {

	private static DocumentBuilder builder;

	static {
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public XMLProtocol() {
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
				bars.put(key, new Bar(Double.valueOf(nodeValue), Double.valueOf(node.getAttribute("maximum"))));
				break;
			case "AbilityHandler":
				abilityHandler = (AbilityHandler) decode(node);
				break;
			case "FireballSpell":
				abilities.add((FireballSpell) decode(node));
				break;
			case "HasteSpell":
				abilities.add((HasteSpell) decode(node));
				break;
			case "RocketSpell":
				abilities.add((RocketSpell) decode(node));
				break;
			}
		}
		switch (e.getTagName()) {
		case "Block":
		case "Air":
		case "ManaPotion":
		case "HealthPotion":
			try {
				Thing element = (Thing) Class.forName("rpg.element." + e.getTagName()).getConstructor(Vector2D.class)
						.newInstance(thing.getVector("location"));
				for (String key : thing.getKeys()) {
					element.set(key, thing.get(key));
				}
				return element;
			} catch (Exception ex) {
				ex.printStackTrace();
				break;
			}

		case "Fireball":
		case "Rocket":
			try {
				Thing element = (Thing) Class.forName("rpg.element." + e.getTagName())
						.getConstructor(Vector2D.class, Vector2D.class, double.class).newInstance(
								thing.getVector("location"), thing.getVector("direction"), thing.getNumber("speed"));
				for (String key : thing.getKeys()) {
					element.set(key, thing.get(key));
				}
				return element;
			} catch (Exception ex) {
				ex.printStackTrace();
				break;
			}
		case "Portal":
			try {
				Thing element = (Thing) Class.forName("rpg.element." + e.getTagName())
						.getConstructor(Vector2D.class, Vector2D.class)
						.newInstance(thing.getVector("location"), thing.getVector("target"));
				for (String key : thing.getKeys()) {
					element.set(key, thing.get(key));
				}
				return element;
			} catch (Exception ex) {
				ex.printStackTrace();
				break;
			}
		case "Player":
		case "Dragon":
			try {
				Entity entity = (Entity) Class.forName("rpg.element.entity." + e.getTagName())
						.getConstructor(Vector2D.class, Race.class)
						.newInstance(thing.getVector("location"), Race.valueOf(thing.getString("race")));
				for (String key : thing.getKeys()) {
					entity.set(key, thing.get(key));
				}
				for (Entry<String, Bar> entry : bars.entrySet()) {
					entity.putBar(entry.getKey(), entry.getValue());
				}
				if (abilityHandler != null) {
					abilityHandler.getAbilities().forEach(a -> entity.getAbilityHandler().addAbility(a));
				}
				return entity;
			} catch (Exception ex) {
				ex.printStackTrace();
				break;
			}
		case "AbilityHandler":
			AbilityHandler ah = new AbilityHandler();
			abilities.forEach(a -> ah.addAbility(a));
			return ah;
		case "FireballSpell":
			return new FireballSpell(thing.getNumber("speed"), thing.getNumber("distance"));
		case "HasteSpell":
			return new HasteSpell();
		case "RocketSpell":
			return new RocketSpell(thing.getNumber("speed"));

		}
		throw new RPGException("No match for " + e.getTagName());
	}

	public static void main(String[] args) {
		Player p = new Player(Vector2D.NORTH, Race.HUMAN);
		p.getAbilityHandler().addAbility(new FireballSpell(3, 32));
		XMLProtocol protocol = new XMLProtocol();
		Transformer t;
		DocumentBuilder db;
		File f = new File("xml/player.xml");
		try {
			t = TransformerFactory.newInstance().newTransformer();
			t.transform(new DOMSource(protocol.encode(p)), new StreamResult(System.out));
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			System.out.println();
			t.transform(new DOMSource(protocol.encode(protocol.decode(db.parse(f).getFirstChild()))),
					new StreamResult(System.out));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Node encode(Thing thing) {
		return encode0(thing, builder.newDocument());
	}

	public Node encode0(Thing thing, Document document) {
		org.w3c.dom.Element root = document.createElement(thing.getClass().getSimpleName());
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
			org.w3c.dom.Element barsNode = document.createElement("Map");
			barsNode.setAttribute("name", "bars");
			for (Entry<String, Bar> entry : bars.entrySet()) {
				org.w3c.dom.Element entryNode = document.createElement("Bar");
				entryNode.setAttribute("key", entry.getKey());
				entryNode.setAttribute("value", "" + entry.getValue().getValue());
				entryNode.setAttribute("maximum", "" + entry.getValue().getMaximum());
				barsNode.appendChild(entryNode);
			}
			root.appendChild(barsNode);

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
