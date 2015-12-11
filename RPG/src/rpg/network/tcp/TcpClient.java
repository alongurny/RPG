package rpg.network.tcp;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class TcpClient implements Closeable {

	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;

	public TcpClient(String host, int port) throws IOException {
		this(InetAddress.getByName(host), port);
	}

	public TcpClient(InetAddress host, int port) throws IOException {
		socket = new Socket(host, port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
	}

	public void send(String data) {
		out.println(data);
	}

	public String receive() throws IOException {
		return in.readLine();
	}

	@Override
	public void close() throws IOException {
		socket.close();
	}
}
