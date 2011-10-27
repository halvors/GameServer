package org.halvors.Game.Server.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.network.packet.Packet;
import org.halvors.Game.Server.network.packet.PacketLogin;

public class AcceptThread extends Thread {
	private final GameServer server;
	private final ListenThread listenThread;
	private final Queue<Socket> pendingConnections = new LinkedList<Socket>();
	
	public AcceptThread(GameServer server, ListenThread listenThread) {
		this.server = server;
		this.listenThread = listenThread;
	}
	
	@Override
	public void run() {
		Socket socket = null;
		DataInputStream input = null;
		Packet packet = null;
		LoginHandler loginHandler = null;
		
		while (true) {
			if (!pendingConnections.isEmpty()) {
				socket = pendingConnections.poll();
				
				try {
					input = new DataInputStream(socket.getInputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				packet = Packet.readPacket(input);
				
				if (packet != null && packet instanceof PacketLogin) {
					loginHandler = new LoginHandler(server, listenThread.addClient(socket));
					loginHandler.handleLogin((PacketLogin) packet);
				}
			} else {
				try {	
					Thread.sleep(1000L);	
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void addToPendigConnections(Socket socket) {
		// We don't want to accept the socket twice.
		if (!pendingConnections.contains(socket)) {
			pendingConnections.add(socket);
		}
	}
	
	public void removeFromPendigConnections(Socket socket) {
		// We only want to remove socket that exist's.
		if (pendingConnections.contains(socket)) {
			pendingConnections.remove(socket);
		}
	}

	public ListenThread getListenThread() {
		return listenThread;
	}
}