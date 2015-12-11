package rpg.network.tcp;

import java.io.IOException;
import java.net.InetAddress;

public class KnockKnockTcpClient extends TcpClient {

	public KnockKnockTcpClient(InetAddress host, int port) throws IOException {
		super(host, port);
	}
	
	public KnockKnockTcpClient(String host, int port) throws IOException {
		super(host, port);
	}
	
	public String sendReceive(String data) throws IOException {
		send(data);
		return receive();
	}
	
}
