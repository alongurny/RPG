package rpg.network;

import rpg.element.Player;
import rpg.geometry.Vector2D;
import rpg.logic.level.Level;
import tcp.TcpClient;

public class NetworkCommand {

	public static void execute(String string, Level level, TcpClient client) {
		String[] arr = string.split(" ");
		Player player = level.getPlayer(client).get();
		String command = arr[0];
		switch (command) {
		case "moveBy":
			level.tryMoveBy(player, Vector2D.valueOf(arr[1]));
			break;
		case "setLocation":
			player.setLocation(Vector2D.valueOf(arr[1]));
			break;
		case "setDirection":
			player.setDirection(Vector2D.valueOf(arr[1]));
			break;
		case "onClick":
			level.onClick(player, Vector2D.valueOf(arr[1]));
			break;
		case "cast":
			player.tryCast(level, Integer.parseInt(arr[1]), player.getTarget());
			break;
		case "interact":
			level.tryInteract(player);
			break;
		}
	}

}
