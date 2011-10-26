package org.halvors.Game.Server.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import org.halvors.Game.Server.GameServer;

public class NetworkListenThread extends Thread {
	private final GameServer server;
	private final ServerSocket serverSocket;
	private final NetworkAcceptThread networkAcceptThread;
	private final LoginHandler loginHandler;
	private final List<NetworkManager> clients = Collections.synchronizedList(new ArrayList<NetworkManager>());
	
	public NetworkListenThread(GameServer server, InetAddress address, int port) throws IOException {
		this.server = server;
		this.serverSocket = new ServerSocket(port, 0, address);
		this.loginHandler = new LoginHandler(server);
		
		// Accept connections and logins here before we register a new NetworkManager.
		this.networkAcceptThread = new NetworkAcceptThread(server, loginHandler);
		networkAcceptThread.start();
	}
	
	public void run() {
		while (!serverSocket.isClosed()) {
			try {
				Socket socket = serverSocket.accept();
				
				if (socket != null && socket.isBound()) {
					// Add the socket to the pending connections list.
					networkAcceptThread.addToPendigConnections(socket);
					//networkAcceptThread.
				
					server.log(Level.INFO, "Connection accepted from: " + socket.getRemoteSocketAddress().toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public List<NetworkManager> getClients() {
		return clients;
	}
	
	public boolean hasClient(Socket socket) {
		for (NetworkManager n : clients) {
			Socket s = n.getSocket();
			
			if (socket.getInetAddress() == s.getInetAddress() && socket.getPort() == s.getPort()) {
				return true;
			}
		}
		
		return false;
	}
	
	public void addClient(Socket socket) {
		if (!clients.contains(socket)) {
			// Create a new NetworkManager and add it to the clients list.
			NetworkManager networkManager = new NetworkManager(socket);
			clients.add(networkManager);
		}
	}
	
	public void removeClient(NetworkManager networkManager) {
		if (clients.contains(networkManager)) {
			clients.remove(networkManager);
		}
	}
}
