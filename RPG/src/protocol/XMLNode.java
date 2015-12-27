package protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLNode {
	private List<XMLNode> children;
	private Map<String, String> attributes;
	private String type;

	public XMLNode(String header) {
		children = new ArrayList<>();
		attributes = new HashMap<>();
		this.type = header;
	}

	public List<XMLNode> getChildren() {
		return new ArrayList<>(children);
	}

	public void addAttribute(String key, String value) {
		attributes.put(key, value);
	}

	public void addChild(XMLNode child) {
		children.add(child);
	}

	public String getName() {
		return type;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("<" + type);
		attributes.forEach((key, value) -> sb.append(" " + key + "='" + value + "'"));
		sb.append(">");
		children.forEach(child -> sb.append(child));
		sb.append("</" + type + ">");
		return sb.toString();
	}
}
