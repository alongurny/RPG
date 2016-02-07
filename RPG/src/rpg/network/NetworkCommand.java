package rpg.network;

import rpg.element.Player;
import rpg.geometry.Vector2D;
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
		String[] arr = string.split(" ");
		if (arr[0].equals("player")) {
			Player player = level.getPlayer(Integer.parseInt(arr[1]));
			switch (arr[2]) {
			case "moveBy":
				level.tryMoveBy(player, Vector2D.valueOf(arr[3]));
				break;
			case "cast":
				player.tryCast(level, Integer.parseInt(arr[3]));
				break;
			case "interact":
				level.tryInteract(player);
				break;
			case "setVector":
				player.set(arr[3], Vector2D.valueOf(arr[4]));
				break;
			}

		} else {
			throw new RuntimeException(string);
		}
	}

}
