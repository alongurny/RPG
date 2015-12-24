package event;

@FunctionalInterface
public interface MessageListener {
	void onReceive(MessageEvent e);
}
