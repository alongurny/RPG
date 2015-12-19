package rpg.ui;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import rpg.Game;
import rpg.ability.FireballSpell;
import rpg.element.entity.AttributeSet;
import rpg.element.entity.Player;
import rpg.element.entity.Profession;
import rpg.element.entity.Race;
import rpg.level.Level;
import rpg.level.Level1;
import rpg.physics.Vector2D;

public class GameStation {

	private MetaBoard metaBoard;
	private GameBoard gameBoard;
	private Game game;

	public GameStation(Game game, Player player) {
		metaBoard = new MetaBoard(480, 120, game, player);
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
		gameBoard.setLocation(500, 100);
		metaBoard.setFocusableWindowState(false);
		this.game = game;
	}

	public void start() {
		metaBoard.setVisible(true);
		gameBoard.setVisible(true);
		new Thread(() -> {
			while (true) {
				game.update();
				gameBoard.repaint();
				metaBoard.repaint();
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}

	public static void main(String[] args) {
		Player player = new Player(new Vector2D(100, 100), new AttributeSet(13, 13, 13), Race.HUMAN, Profession.MAGE);
		player.getAbilityHandler().addAbility(new FireballSpell(7));
		Level level = new Level1(player);
		Game game = new Game(level);
		GameStation gs = new GameStation(game, player);
		gs.start();

	}

}