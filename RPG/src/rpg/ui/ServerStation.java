package rpg.ui;

import rpg.logic.level.Game;
import rpg.network.GameServer;

public class ServerStation {

	private Game game;
	private GameServer server;

	public ServerStation(GameServer server, Game game) {
		this.game = game;
		this.server = server;
	}

	public void start() {
		new Thread(() -> {
			server.startReceiving();
			while (!game.isReady()) {
				try {
					Thread.sleep(50);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			server.startSending();
			long last = System.nanoTime();
			while (true) {
				long now = System.nanoTime();
				game.update((now - last) * 1e-9);
				last = now;
			}
		}).start();
	}

}
