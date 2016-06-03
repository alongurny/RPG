package rpg.ui;

import rpg.logic.Game;

public class ServerStation {

	private Game game;

	public ServerStation(Game game) {
		this.game = game;
	}

	public void start() {
		new Thread(() -> {
			long last = System.nanoTime();
			while (true) {
				long now = System.nanoTime();
				game.update((now - last) * 1e-9);
				last = now;
			}
		}).start();
	}

}
