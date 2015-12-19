package rpg.ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import rpg.element.entity.Player;
import rpg.logic.Game;

public class MetaPanel extends JPanel {

	private List<Drawable> drawables;

	public MetaPanel(Game game, Player player) {
		drawables = new ArrayList<>();
	}

	public void addDrawable(Drawable drawable) {
		drawables.add(drawable);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.translate(20, 20);
		for (Drawable d : drawables) {
			d.draw(g);
			g.translate(0, 64);
		}
	}

}
