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
	
	public AcceptThread(String name, GameServer server, ListenThread listenThread) {
		super(name);
		this.server = server;
		this.listenThread = listenThread;
	}
	
	@Override
	public void run() {
		Socket socket = null;
		DataInputStream input = null;
		Packet packet = null;
		LoginHandler loginHandler = null;
		
		while (server.isRunning()) { // TODO: Rewrite this?
			if (!pendingConnections.isEmpty()) {
				try {
					socket = pendingConnections.poll();
					input = new DataInputStream(socket.getInputStream());
				
					packet = Packet.readPacket(input);
				
					if (packet != null && packet instanceof PacketLogin) {
						loginHandler = new LoginHandler(server, socket);
						loginHandler.handleLogin((PacketLogin) packet);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
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