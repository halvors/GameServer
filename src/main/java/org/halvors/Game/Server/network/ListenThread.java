package org.halvors.Game.Server.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;

import org.halvors.Game.Server.GameServer;

public class ListenThread extends Thread {
	private final GameServer server;
	private final AcceptThread acceptThread;
	
	private ServerSocket serverSocket;
	
	public ListenThread(String name, GameServer server, InetAddress address, int port) {
		super(name);
		
		this.server = server;
		
		// Start listening.
		try {
			serverSocket = new ServerSocket(port, 0, address);
			
			server.log(Level.INFO, "Server is running on port: "+ Integer.toString(port));
		} catch (IOException e) {
			server.log(Level.WARNING, "Failed to bind to port: " + Integer.toString(port));
			e.printStackTrace();
		}
		
		// Accept connections and login before we register a new NetworkManager.
		this.acceptThread = new AcceptThread("Accept thread", server, this);
		acceptThread.start();
	}
	
	@Override
	public void run() {
		Socket socket = null;
		
		while (server.isRunning()) {
			try {
				socket = serverSocket.accept();
				
				if (socket != null) {
					// Add the socket to the queue.
					synchronized (acceptThread) {
						acceptThread.queue(socket);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public GameServer getServer() {
		return server;
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
}