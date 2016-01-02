package rpg.ui;

import javax.swing.JFrame;

import rpg.logic.Game;

public class MetaBoard extends JFrame {

	private static final long serialVersionUID = -4709290312794276857L;

	private MetaPanel metaPanel;

	public MetaBoard(int width, int height, Game game, int num) {
		setSize(width, height);
		setResizable(false);
		metaPanel = new MetaPanel(game, num);
		metaPanel.addDrawable(new AbilityHandlerDrawer(game, num));
		add(metaPanel);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

}
