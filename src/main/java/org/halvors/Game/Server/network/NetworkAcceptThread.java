package main.java.org.halvors.Game.Server.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;

import main.java.org.halvors.Game.Server.GameServer;
import main.java.org.halvors.Game.Server.LoginHandler;
import main.java.org.halvors.Game.Server.network.packet.Packet;
import main.java.org.halvors.Game.Server.network.packet.PacketLogin;

public class NetworkAcceptThread extends Thread {
	private final GameServer server;
	private final Queue<Socket> pendingConnections = new LinkedList<Socket>();
	
	public NetworkAcceptThread(GameServer server, LoginHandler loginHandler) {
		this.server = server;
	}
	//public boolean iswaiting = false;
	public void run() {
		Socket socket = null;
		DataInputStream in = null;
		Packet packet = null;
		
		while (true) {
			
				System.out.println("________________________________________________________________________"+Integer.toString(pendingConnections.size()));
					if (!pendingConnections.isEmpty()) {
					//wait();
						
						socket = pendingConnections.poll();
						try {
					in = new DataInputStream(socket.getInputStream());
						} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				packet = Packet.getNewPacket(1);
				
				if (packet != null && packet instanceof PacketLogin) {
					LoginHandler loginHandler = new LoginHandler(server);
					loginHandler.handleLogin((PacketLogin) packet);
				}
				
				
				}
					if (pendingConnections.isEmpty()) {
						try {
							
							this.sleep(1000l);
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
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
}
