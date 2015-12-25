package rpg.ui;

import javax.swing.JFrame;

import rpg.element.entity.Player;
import rpg.logic.Game;

public class MetaBoard extends JFrame {

	private static final long serialVersionUID = -4709290312794276857L;

	private MetaPanel metaPanel;

	public MetaBoard(int width, int height, Game game, Player player) {
		setSize(width, height);
		setResizable(false);
		metaPanel = new MetaPanel(game, player);
		metaPanel.addDrawable(player.getAbilityHandler());
		add(metaPanel);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

}
