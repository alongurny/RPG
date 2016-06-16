package rpg.server;

import java.io.IOException;

import rpg.logic.level.Game;
import rpg.logic.level.MoreInterestingGame;

public class RunServer {
	public static void main(String[] args) throws IOException {
		Game game = new MoreInterestingGame();
		GameServer server = new GameServer(game);
		server.start();
	}
}
