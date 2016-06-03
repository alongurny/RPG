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

	protected abstract void handleSocket(Socket s) throws IOException;

	public void start() {
		if (running) {
			throw new IllegalStateException("Server already running");
		}
		System.out.println("Server started!");
		running = true;
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
					e.printStackTrace();
				}
			}
		} , "Main TCP Server Thread").start();
	}

	public void stop() {
		System.out.println("Server stopped!");
		running = false;
	}

}
