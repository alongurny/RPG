package rpg.network.event;

@FunctionalInterface
public interface MessageListener {
	void onReceive(MessageEvent e);
}
