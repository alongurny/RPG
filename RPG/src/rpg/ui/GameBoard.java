package rpg.ui;

import javax.swing.JFrame;

import rpg.AbilityDrawer;
import rpg.AttributeSet;
import rpg.FireballSpell;
import rpg.Game;
import rpg.Race;
import rpg.element.Player;
import rpg.level.Level;
import rpg.level.Level1;
import rpg.physics.Vector2D;

public class GameBoard extends JFrame {

	private MetaPanel metaPanel;
	private GamePanel gamePanel;
	private Thread thread;

	public GameBoard(int width, int height, Game game, Player player) {
		setResizable(false);
		setLocation(480, 48);
		metaPanel = new MetaPanel();
		metaPanel.addDrawable(new AbilityDrawer(0, 0, null));
		gamePanel = new GamePanel(game, player);
		add(metaPanel);
		add(gamePanel);
		setSize(width, height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		thread = new Thread(() -> {
			while (true) {
				game.update();
				repaint();
				try {
					Thread.sleep(1000 / 24);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void run() {
		setVisible(true);
		thread.start();
	}

	public static void main(String[] args) {
		Player player = new Player(new Vector2D(100, 100), new AttributeSet(13, 13, 13), Race.HUMAN);
		player.getAbilityHandler().addAbility(new FireballSpell(player, 7));
		Level level = new Level1(player);
		Game game = new Game(level);
		GameBoard b = new GameBoard(512, 512, game, player);
		level.setNextLevel(new Level1(player));
		b.run();

	}

}
