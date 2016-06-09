package rpg.network;

import network.TcpClient;
import rpg.element.entity.Player;
import rpg.geometry.Vector2D;
import rpg.logic.level.Game;

public class NetworkCommand {

	public static void execute(String string, Game game, TcpClient client) {
		String[] arr = string.split(" ");
		Player player = game.getPlayer(client).get();
		String command = arr[0];
		switch (command) {
		case "moveBy":
			game.getObstaclesFromMoveBy(player, Vector2D.valueOf(arr[1]));
			break;
		case "setLocation":
			player.setLocation(Vector2D.valueOf(arr[1]));
			break;
		case "jump":
			player.tryJump();
			break;
		case "fall":
			player.tryFall();
			break;
		case "moveHorizontally":
			player.moveHorizontally(Double.parseDouble(arr[1]));
			break;
		case "onClick":
			game.onClick(player, Vector2D.valueOf(arr[1]));
			break;
		case "cast":
			player.tryCast(game, Integer.parseInt(arr[1]));
			break;
		case "interact":
			game.tryInteract(player);
			break;
		}
	}

}
