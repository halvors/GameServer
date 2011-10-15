package main.java.org.halvors.Game.Server.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import main.java.org.halvors.Game.Server.GameServer;
import main.java.org.halvors.Game.Server.LoginHandler;

public class NetworkListenThread extends Thread {
	private final GameServer server;
	private final ServerSocket serverSocket;
	private final List<NetworkManager> clients = Collections.synchronizedList(new ArrayList<NetworkManager>());
	private final LoginHandler loginHandler;
	
	public NetworkListenThread(GameServer server, InetAddress address, int port) throws IOException {
		this.server = server;
		this.serverSocket = new ServerSocket(port, 0, address);
		this.loginHandler = new LoginHandler(server);
	}
	
	public void run() {
		while (!serverSocket.isClosed()) {
			try {
				Socket socket = serverSocket.accept();
				
				if (socket != null && socket.isBound()) {
					// Add the socket to the clients list.
					addClient(socket);
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

	public LoginHandler getLoginHandler() {
		return loginHandler;
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
			NetworkManager networkManager = new NetworkManager(socket, loginHandler);
			clients.add(networkManager);
		}
	}
	
	public void removeClient(NetworkManager networkManager) {
		if (clients.contains(networkManager)) {
			clients.remove(networkManager);
		}
	}
}
