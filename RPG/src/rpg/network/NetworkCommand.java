package rpg.network;

import rpg.logic.level.Level;

public class NetworkCommand {

	private String string;

	public NetworkCommand(String string) {
		this.string = string;
	}

	@Override
	public String toString() {
		return string;
	}

	public void execute(Level level) {

	}

}
