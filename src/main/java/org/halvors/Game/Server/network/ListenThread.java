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

public class ListenThread extends Thread {
	private final GameServer server;
	private final ServerSocket serverSocket;
	private final AcceptThread networkAcceptThread;
	private final List<NetworkManager> clients = Collections.synchronizedList(new ArrayList<NetworkManager>());
	
	public ListenThread(GameServer server, InetAddress address, int port) throws IOException {
		this.server = server;
		this.serverSocket = new ServerSocket(port, 0, address);
		
		// Accept connections and logins here before we register a new NetworkManager.
		this.networkAcceptThread = new AcceptThread("Accept thread", server, this);
		networkAcceptThread.start();
	}
	
	@Override
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
	
	public NetworkManager getClient(NetworkManager networkManager) {
		for (NetworkManager n : clients) {
			if (networkManager.equals(networkManager)) {
				return n;
			}
		}
		
		return null;
	}
	
	public NetworkManager addClient(NetworkManager networkManager) {
		if (!clients.contains(networkManager)) {
			// Create a new NetworkManager and add it to the clients list.
			clients.add(networkManager);
			
			return networkManager;
		}
		
		return null;
	}
	
	public void removeClient(NetworkManager networkManager) {
		if (clients.contains(networkManager)) {
			clients.remove(networkManager);
		}
	}
}
