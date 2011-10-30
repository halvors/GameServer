package org.halvors.Game.Server.network;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.entity.Player;
import org.halvors.Game.Server.network.packet.Packet;
import org.halvors.Game.Server.network.packet.PacketDisconnect;

public class NetworkManager {
	private final GameServer server;
	private final Socket socket;
	private final LoginHandler loginHandler;
	private final Queue<Packet> packetQueue = new LinkedList<Packet>();
	private final Thread readThread;
	private final Thread writeThread;
	
	private boolean isRunning = true;
	
	public NetworkManager(GameServer server, Socket socket, LoginHandler loginHandler, String name) throws IOException {
		this.server = server;
		this.socket = socket;
		socket.setSoTimeout(30000);
        socket.setTrafficClass(24); // TODO: Check this?
		this.loginHandler = loginHandler;
		this.readThread = new ReaderThread(name + ": Reader thread", this);
        this.writeThread = new WriterThread(name + ": Writer thread", this);
        readThread.start();
        writeThread.start();
	}
	
	/**
	 * Simply send a single packet.
	 * 
	 * @param packet
	 */
	public void sendPacket(Packet packet) {
        if (packet != null) {
        	packetQueue.add(packet);
        }
    }
	
	/**
	 * Send a packet to all connected players.
	 * 
	 * @param packet
	 */
	public void broadcastPacket(Packet packet) {
		for (Player player : GameServer.getInstance().getPlayers()) {
			player.getNetworkServerHandler().getNetworkManager().sendPacket(packet);
		}
	}
	
	public void disconnect(String reason) {
		sendPacket(new PacketDisconnect(reason));
		wakeThreads();
	}
	
	public void wakeThreads() {
		readThread.interrupt();
		writeThread.interrupt();
	}

	public Socket getSocket() {
		return socket;
	}

	public GameServer getServer() {
		return server;
	}

	public LoginHandler getLoginHandler() {
		return loginHandler;
	}
	
	public ServerHandler getServerHandler() {
		return loginHandler.getServerHandler();
	}
	
	public Queue<Packet> getPacketQueue() {
		return packetQueue;
	}
	
	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
}