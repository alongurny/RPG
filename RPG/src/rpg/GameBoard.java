package rpg;

import javax.swing.JFrame;

import physics.Vector;

public class GameBoard extends JFrame {

	private GamePanel panel;

	public GameBoard() {
		panel = new GamePanel(new Game());
		panel.getGame().addElement(new Player(Vector.ZERO, new AttributeSet(13, 13, 13), Race.HUMAN));
		add(panel);
		setSize(640, 640);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		GameBoard b = new GameBoard();
		new Thread(() -> {
			while (true) {
				b.repaint();
				try {
					Thread.sleep(1000 / 24);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		new Thread(() -> {
			while (true) {
				b.panel.getGame().update();
				try {
					Thread.sleep(1000 / 24);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
