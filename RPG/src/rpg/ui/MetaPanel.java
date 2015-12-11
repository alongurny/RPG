package rpg.ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class MetaPanel extends JPanel {

	private List<Drawable> drawables;

	public MetaPanel() {
		drawables = new ArrayList<>();
	}

	public void addDrawable(Drawable drawable) {
		drawables.add(drawable);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Drawable d : drawables) {
			d.draw(g);
		}
	}

}
