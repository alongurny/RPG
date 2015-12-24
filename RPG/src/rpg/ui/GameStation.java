package rpg.ui;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.Socket;

import rpg.ability.FireballSpell;
import rpg.ability.HasteSpell;
import rpg.ability.RocketSpell;
import rpg.element.entity.AttributeSet;
import rpg.element.entity.Player;
import rpg.element.entity.Profession;
import rpg.element.entity.Race;
import rpg.logic.Game;
import rpg.logic.Level;
import rpg.logic.Level1;
import rpg.physics.Vector2D;

public abstract class GameStation {

	private MetaBoard metaBoard;
	private GameBoard gameBoard;
	private Game game;

	public GameStation(Game game, Player player) {
		metaBoard = new MetaBoard(480, 200, game, player);
		gameBoard = new GameBoard(480, 480, game, player);
		gameBoard.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
			}

			@Override
			public void componentResized(ComponentEvent e) {

			}

			@Override
			public void componentMoved(ComponentEvent e) {
				metaBoard.setLocation(gameBoard.getLocation().x,
						gameBoard.getLocation().y + (int) gameBoard.getSize().getHeight());
			}

			@Override
			public void componentHidden(ComponentEvent e) {

			}
		});
		gameBoard.setLocation(500, 40);
		this.game = game;
	}

	public void addKeyListener(KeyListener listener) {
		gameBoard.addKeyListener(listener);
	}

	public void start() {
		metaBoard.setVisible(true);
		gameBoard.setVisible(true);
		new Thread(() -> {
			long last = System.currentTimeMillis();
			while (true) {
				try {
					Thread.sleep(16);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				doSomething();
				gameBoard.repaint();
				metaBoard.repaint();
				long now = System.currentTimeMillis();
				game.update(20e-3);
				long diff = (now - last);
				if (diff < 20) {
					try {
						Thread.sleep(19 - diff);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				last = now;

			}
		}).start();

	}

	public abstract void doSomething();

	public static void main(String[] args) {
		Player player = new Player(new Vector2D(80, 100), new AttributeSet(), Race.HUMAN, Profession.MAGE);
		player.getAbilityHandler().addAbility(new FireballSpell(192));
		player.getAbilityHandler().addAbility(new RocketSpell(160));
		player.getAbilityHandler().addAbility(new HasteSpell());
		Level level = new Level1(player);
		Game game = new Game(level);

		Socket s = null;
		try {
			s = new Socket("localhost", 1234);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PlayerClient client = new PlayerClient(game, player, s);

		GameStation gs = new GameStation(game, player) {
			@Override
			public void doSomething() {
				client.run();
			}
		};
		KeyTracker keyTracker = new KeyTracker();
		keyTracker.addMultiKeyListener(client);
		gs.addKeyListener(keyTracker);
		gs.addKeyListener(client);
		gs.start();

	}

}
