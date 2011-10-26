package org.halvors.Game.Server.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.network.packet.Packet;
import org.halvors.Game.Server.network.packet.PacketLogin;

public class NetworkAcceptThread extends Thread {
	private final GameServer server;
	private final LoginHandler loginHandler;
	private final Queue<Socket> pendingConnections = new LinkedList<Socket>();
	
	public NetworkAcceptThread(GameServer server, LoginHandler loginHandler) {
		this.server = server;
		this.loginHandler = loginHandler;
	}
	//public boolean iswaiting = false;
	public void run() {
		Socket socket = null;
		DataInputStream input = null;
		Packet packet = null;
		
		while (true) {
			System.out.println("________________________________________________________________________"+Integer.toString(pendingConnections.size()));
			
			if (!pendingConnections.isEmpty()) {
				socket = pendingConnections.poll();
				
				try {
					input = new DataInputStream(socket.getInputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				packet = Packet.readPacket(input);
				
				if (packet != null && packet instanceof PacketLogin) {
					LoginHandler loginHandler = new LoginHandler(server);
					loginHandler.handleLogin((PacketLogin) packet);
				}
			} else {
				try {	
					Thread.sleep(1000l);	
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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
	
	public LoginHandler getLoginHandler() {
		return loginHandler;
	}
}
