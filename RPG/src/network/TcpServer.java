package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import rpg.exception.RPGException;

public abstract class TcpServer {

	private ServerSocket socket;
	private boolean running;

	/**
	 * Constructs a new server at the given port.
	 * 
	 * @param port
	 *            the port
	 * @throws IOException
	 */
	protected TcpServer(int port) throws IOException {
		socket = new ServerSocket(port);
	}

	/**
	 * Returns <code>true</code> if this server is currently running, otherwise
	 * <code>false</code>
	 * 
	 * @return <code>true</code> if this server is currently running, otherwise
	 *         <code>false</code>
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Starts this server. A start thread will notify the caller that it's
	 * started and then will start looking for new sockets to handle in a
	 * separate thread. Each new found socket will be assigned it's own thread,
	 * which will run the {@link #handleSocket(Socket) handleSocket} method.
	 * 
	 * @see #stop()
	 */
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

	/**
	 * Stops this server. It is the opposite of {@link #start() start}: It will
	 * stop looking for new sockets to handle. Note that {@link #start() start}
	 * cannot be called after <code>stop</code> is called.
	 * 
	 * @see #start()
	 */
	public void stop() {
		System.out.println("Server stopped!");
		try {
			socket.close();
			running = false;
		} catch (IOException e) {
			throw new RPGException(e);
		}
	}

	/**
	 * This method is intended to be implemented by subclasses. It will be
	 * called each time a new socket is found.
	 * 
	 * @param socket
	 *            the found socket
	 * @throws IOException
	 */
	protected abstract void handleSocket(Socket socket) throws IOException;

}
