package rpg.network.event;

public class DataEvent {
	
	private byte data;
	
	public DataEvent(byte data) {
		this.data = data;
	}

	public byte getData() {
		return data;
	}
	
}
