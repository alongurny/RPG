package rpg.network;

import rpg.element.entity.Player;
import rpg.logic.Game;
import rpg.physics.Vector2D;

public class NetworkCommand {

	private String string;

	public NetworkCommand(String string) {
		this.string = string;
	}

	@Override
	public String toString() {
		return string;
	}

	public void execute(Game game) {
		String[] arr = string.split(" ");
		if (arr[0].equals("player")) {
			Player player = game.getLevel().getPlayer(Integer.parseInt(arr[1]));
			switch (arr[2]) {
			case "moveBy":
				game.getLevel().tryMoveBy(player, Vector2D.valueOf(arr[3]));
				break;
			case "cast":
				player.getAbilityHandler().tryCast(game.getLevel(), Integer.parseInt(arr[3]));
				break;
			case "interact":
				game.getLevel().tryInteract(player);
				break;
			case "setVector":
				player.setVector(arr[3], Vector2D.valueOf(arr[4]));
				break;
			}
		} else {
			throw new RuntimeException(string);
		}
	}

}
