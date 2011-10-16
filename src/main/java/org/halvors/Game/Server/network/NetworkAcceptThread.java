package main.java.org.halvors.Game.Server.network;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import main.java.org.halvors.Game.Server.GameServer;

public class NetworkAcceptThread extends Thread {
	private final GameServer server;
	private final List<Socket> pendingConnections = new ArrayList<Socket>();
	
	public NetworkAcceptThread(GameServer server) {
		this.server = server;
	}
	
	public void run() {
		Socket socket = null;
		
		while (!pendingConnections.isEmpty()) {
			
			
			
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void addToPendigConnections(Socket socket) {
		// We don't wanna accept 2 of the same socket.
		if (!pendingConnections.contains(socket)) {
			pendingConnections.add(socket);
		}
	}
	
	public void removeFromPendigConnections(Socket socket) {
		// We only wanna remove socket that exist's.
		if (pendingConnections.contains(socket)) {
			pendingConnections.remove(socket);
		}
	}
}
