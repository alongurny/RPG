package rpg.network.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class KnockKnockTcpServer extends TcpServer {

	public KnockKnockTcpServer(int port) throws IOException {
		super(port);
	}

	protected abstract String process(String s);

	@Override
	protected void handleSocket(Socket s) {
		System.out.println("Handling request from "
				+ s.getInetAddress().getHostName());
		try (BufferedReader in = new BufferedReader(new InputStreamReader(
				s.getInputStream()));
				PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
			StringBuilder sb = new StringBuilder();
			for (String input = in.readLine(); input != null
					&& input.length() > 0; input = in.readLine()) {
				sb.append(input + "\n");
			}
			out.println(process(sb.toString()));
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
