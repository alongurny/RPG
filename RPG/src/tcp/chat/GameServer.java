package tcp.chat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import event.MessageEvent;
import event.MessageListener;
import rpg.ability.FireballSpell;
import rpg.ability.HasteSpell;
import rpg.ability.RocketSpell;
import rpg.element.Element;
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
import tcp.chat.message.Message;
import tcp.chat.message.Message.Source;

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
		GameServer _server = null;
		try {
			_server = new GameServer(game);
		} catch (IOException e) {
			e.printStackTrace();
		}
		final GameServer server = _server;
		GameStation gs = new GameStation(game, player) {
			@Override
			public void doSomething() {
				System.out.println("started");
				StringBuilder sb = new StringBuilder();
				int i = 0;
				for (Element element : game.getLevel().getDynamicElements()) {
					try {
						System.out.println("object " + i++);

						ByteArrayOutputStream bo = new ByteArrayOutputStream();
						ObjectOutputStream so = new ObjectOutputStream(bo);
						so.writeObject(element);
						so.flush();
						sb.append(bo.toString().replaceAll("\n", "<<newline>>") + "<><>");

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				System.out.println("before send");
				server.inner.send(Message.data(Source.SERVER, sb.toString().trim()));
				System.out.println("ended");
			}
		};
		gs.start();
		server.start();

	}

}
