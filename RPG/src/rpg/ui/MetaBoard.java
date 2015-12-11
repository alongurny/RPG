package rpg.ui;

import javax.swing.JFrame;

import rpg.AbilityDrawer;
import rpg.Game;
import rpg.element.Player;

public class MetaBoard extends JFrame {

	private MetaPanel metaPanel;

	public MetaBoard(int width, int height, Game game, Player player) {
		setSize(width, height);
		setResizable(false);
		metaPanel = new MetaPanel(game, player);
		metaPanel.addDrawable(new AbilityDrawer(0, 0, player.getAbilityHandler()));
		add(metaPanel);
	}

}
