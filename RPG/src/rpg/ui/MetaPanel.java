package rpg.ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import rpg.logic.Game;

public class MetaPanel extends JPanel {

	private static final long serialVersionUID = 3459501081963736465L;

	private List<Drawable> drawables;

	public MetaPanel(Game game, int num) {
		drawables = new ArrayList<>();
	}

	public void addDrawable(Drawable drawable) {
		drawables.add(drawable);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
