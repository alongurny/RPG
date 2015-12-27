package tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class TcpServer {

	private ServerSocket socket;
	private boolean running;

	public TcpServer(int port) throws IOException {
		socket = new ServerSocket(port);
	}

	public void stop() {
		System.out.println("Server stopped!");
		running = false;
	}

	public void start() {
		if (running) {
			throw new IllegalStateException("Server already running");
		}
		running = true;
		System.out.println("Server running!");
		new Thread(() -> {
			while (running) {
				try {
					Socket s = socket.accept();
					new Thread(() -> {
						try {
							handleSocket(s);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} , "Handle " + s.getInetAddress() + " Thread").start();
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}
		} , "Main TCP Server Thread").start();
	}

	protected abstract void handleSocket(Socket s) throws IOException;

}
