package event;

public class ConnectionEvent {
	
	private long when;
	
	public ConnectionEvent() {
		this(System.currentTimeMillis());
	}
	
	public ConnectionEvent(long when) {
		this.when = when;
	}
	
	public long getWhen() {
		return when;
	}
}
