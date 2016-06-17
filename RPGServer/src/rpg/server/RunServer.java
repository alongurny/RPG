package rpg.server;

import java.io.IOException;

import javax.swing.SwingUtilities;

import rpg.logic.level.Game;
import rpg.logic.level.MoreInterestingGame;

public class RunServer {
	public static void main(String[] args) throws IOException {
		Game game = new MoreInterestingGame();
		ServerStation station = new ServerStation(game);
		SwingUtilities.invokeLater(station::start);
	}
}
