package rpg.network.command;

import java.util.HashMap;
import java.util.Map;

public abstract class Command {

	private static final Map<String, Command> names;

	static {
		names = new HashMap<>();
	}

	protected Command(String name) {
		names.put(name, this);
	}

	public static void register(String name, Command command) {
		names.put(name, command);
	}

}
