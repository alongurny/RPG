package rpg.ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import rpg.Game;
import rpg.element.entity.Player;

public class MetaPanel extends JPanel {

	private Player player;
	private List<Drawable> drawables;
	private Game game;

	public MetaPanel(Game game, Player player) {
		drawables = new ArrayList<>();
		this.player = player;
		this.game = game;
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
