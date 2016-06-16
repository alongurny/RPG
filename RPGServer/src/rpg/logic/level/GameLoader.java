package rpg.logic.level;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import rpg.element.Element;
import rpg.element.ItemHolder;
import rpg.geometry.Vector2D;
import rpg.item.Item;

public class GameLoader {

	public static void load(Game game, String path) {
		try (BufferedReader in = new BufferedReader(new FileReader(path))) {
			String line;
			Map<Character, Class<?>> bindings = new HashMap<>();
			Map<Character, Class<?>> staticBindings = new HashMap<>();
			Map<Character, Class<?>> item = new HashMap<>();
			int lineNumber = 0;
			while ((line = in.readLine()) != null) {
				boolean result = processLine(game, line, bindings, staticBindings, item, lineNumber);
				if (result) {
					lineNumber++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean processLine(Game game, String line, Map<Character, Class<?>> bindings,
			Map<Character, Class<?>> staticBindings, Map<Character, Class<?>> items, int lineNumber)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		if (line.startsWith("bind ")) {
			String[] s = line.substring("bind ".length()).split(" ");
			bindings.put(s[1].charAt(0), Class.forName(s[0]));
			return false;
		} else if (line.startsWith("static ")) {
			String[] s = line.substring("static ".length()).split(" ");
			staticBindings.put(s[1].charAt(0), Class.forName(s[0]));
			return false;
		} else if (line.startsWith("item ")) {
			String[] s = line.substring("item ".length()).split(" ");
			items.put(s[1].charAt(0), Class.forName(s[0]));
			return false;
		} else if (line.startsWith("initial ")) {
			String[] s = line.substring("initial ".length()).split(" ");
			game.addInitialLocation(game.getGrid().getLocation(Integer.parseInt(s[0]), Integer.parseInt(s[1])));
			return false;
		} else {
			char[] symbols = line.replace("|", "").toCharArray();
			for (int c = 0; c < symbols.length; c++) {
				char key = symbols[c];
				if (key == 'i') {
					game.addInitialLocation(game.getGrid().getLocation(lineNumber, c));
				} else if (bindings.containsKey(key)) {
					Element element = (Element) bindings.get(key).getConstructor(Vector2D.class)
							.newInstance(game.getGrid().getLocation(lineNumber, c));
					game.addDynamicElement(element);
				} else if (staticBindings.containsKey(key)) {
					Element element = (Element) staticBindings.get(key).getConstructor(Vector2D.class)
							.newInstance(game.getGrid().getLocation(lineNumber, c));
					game.addStaticElement(element);
				} else if (items.containsKey(key)) {
					Element element = new ItemHolder(game.getGrid().getLocation(lineNumber, c),
							(Item) items.get(key).newInstance());
					game.addDynamicElement(element);
				}
			}
			return true;
		}
	}

}
