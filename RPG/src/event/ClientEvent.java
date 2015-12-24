package event;

public class ClientEvent {
	
	private long when;

	public ClientEvent() {
		this(System.currentTimeMillis());
	}
	
	public ClientEvent(long when) {
		this.when = when;
	}

	public long getWhen() {
		return when;
	}
}
