package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import rpg.exception.RPGException;

public abstract class TcpServer {

	private ServerSocket socket;
	private boolean running;

	protected TcpServer(int port) throws IOException {
		socket = new ServerSocket(port);
	}

	public boolean isRunning() {
		return running;
	}

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
					}, "Handle " + s.getInetAddress() + " Thread").start();
				} catch (SocketException e) {
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, "Main TCP Server Thread").start();
	}

	public void stop() {
		System.out.println("Server stopped!");
		try {
			socket.close();
			running = false;
		} catch (IOException e) {
			throw new RPGException(e);
		}
	}

	protected abstract void handleSocket(Socket s) throws IOException;

}