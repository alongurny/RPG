package rpg.ui;

import rpg.logic.Game;

public class ServerStation {

	private Game game;

	public ServerStation(Game game) {
		this.game = game;
	}

	public void start() {
		new Thread(() -> {
			while (!game.getLevel().isReady()) {
				try {
					Thread.sleep(50);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			long last = System.nanoTime();
			while (true) {
				long now = System.nanoTime();
				game.update((now - last) * 1e-9);
				last = now;
			}
		}).start();
	}

}
