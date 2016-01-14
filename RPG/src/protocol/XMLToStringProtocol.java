package protocol;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import rpg.exception.RPGException;

public class XMLToStringProtocol implements Protocol<Node, String> {

	private DocumentBuilder builder;
	private Transformer transformer;

	public XMLToStringProtocol() {
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			transformer = TransformerFactory.newInstance().newTransformer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String encode(Node d) {
		StringWriter sw = new StringWriter();
		try {
			transformer.transform(new DOMSource(d), new StreamResult(sw));
		} catch (TransformerException e) {
			throw new RPGException(e);
		}
		return sw.toString();
	}

	@Override
	public Node decode(String n) {
		try {
			return builder.parse(new InputSource(new StringReader(n))).getFirstChild();
		} catch (Exception e) {
			throw new RPGException(n + " is an invalid string to parse");
		}
	}

}
