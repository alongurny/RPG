package protocol;

import org.w3c.dom.Node;

import rpg.Thing;

public class ThingToStringProtocol implements Protocol<Thing, String> {

	private XMLToStringProtocol xmlToString;
	private ThingToXMLProtocol thingToXML;

	public ThingToStringProtocol() {
		xmlToString = new XMLToStringProtocol();
		thingToXML = new ThingToXMLProtocol();
	}

	@Override
	public Thing decode(String n) {
		Node node = xmlToString.decode(n);
		return thingToXML.decode(node);
	}

	@Override
	public String encode(Thing d) {
		Node node = thingToXML.encode(d);
		return xmlToString.encode(node);
	}

}
