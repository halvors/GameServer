package org.halvors.Game.Server.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.network.packet.Packet;
import org.halvors.Game.Server.network.packet.PacketLogin;
import org.halvors.Game.Server.network.packet.PacketUtil;

public class AcceptThread extends Thread {
	private final GameServer server;
	private final ListenThread listenThread;
	private final Queue<Socket> acceptQueue = new LinkedList<Socket>();
	
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
		
		while (server.isRunning()) {
			try {
				synchronized (acceptQueue) {
					socket = acceptQueue.poll();
					
					if (socket != null) {
						input = new DataInputStream(socket.getInputStream());
						packet = PacketUtil.readPacket(input);
						
						server.log(Level.INFO, "Accepted connection from: " + socket.getRemoteSocketAddress().toString());
						
						if (packet != null && packet instanceof PacketLogin) {
							loginHandler = new LoginHandler(server, socket);
							loginHandler.handleLogin((PacketLogin) packet);
						}
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
	
	public ListenThread getListenThread() {
		return listenThread;
	}
	
	public Queue<Socket> getQueue() {
		return acceptQueue;
	}
	
	public boolean queue(Socket socket) {
		return acceptQueue.add(socket);
	}
	
	public boolean removeFromQueue(Socket socket) {
		return acceptQueue.remove(socket);
	}
}