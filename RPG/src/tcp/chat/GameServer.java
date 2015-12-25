package tcp.chat;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import event.MessageEvent;
import event.MessageListener;
import rpg.ability.FireballSpell;
import rpg.ability.HasteSpell;
import rpg.ability.RocketSpell;
import rpg.element.entity.AttributeSet;
import rpg.element.entity.Player;
import rpg.element.entity.Profession;
import rpg.element.entity.Race;
import rpg.logic.Game;
import rpg.logic.Level;
import rpg.logic.Level1;
import rpg.network.NetworkCommand;
import rpg.physics.Vector2D;
import rpg.ui.GameStation;

public class GameServer {

	private List<NetworkCommand> received;
	private ChatServer inner;
	private Timer timer;
	private Game game;

	public GameServer(Game game) throws IOException {
		received = new CopyOnWriteArrayList<>();
		timer = new Timer();
		inner = new ChatServer();
		inner.addMessageListener(new MessageListener() {
			@Override
			public void onReceive(MessageEvent e) {
				received.add(new NetworkCommand(e.getMessage().getData()));
			}
		});
		this.game = game;
	}

	public void start() {
		inner.start();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				for (NetworkCommand c : received) {
					c.execute(game);
				}
				received.clear();
			}
		}, 0, 10);
	}

	public boolean isAllowed(NetworkCommand command) {
		return true;
	}

	public static void main(String[] args) {
		Player player = new Player(new Vector2D(80, 100), new AttributeSet(), Race.HUMAN, Profession.MAGE);
		player.getAbilityHandler().addAbility(new FireballSpell(192));
		player.getAbilityHandler().addAbility(new RocketSpell(160));
		player.getAbilityHandler().addAbility(new HasteSpell());
		Level level = new Level1(player);
		Game game = new Game(level);
		GameServer server = null;
		try {
			server = new GameServer(game);
		} catch (IOException e) {
			e.printStackTrace();
		}
		GameStation gs = new GameStation(game, player) {
			@Override
			public void doSomething() {

			}
		};
		gs.start();
		server.start();

	}

}
