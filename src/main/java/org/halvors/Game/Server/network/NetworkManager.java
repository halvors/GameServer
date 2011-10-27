package org.halvors.Game.Server.network;

import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.entity.Player;
import org.halvors.Game.Server.network.packet.Packet;

public class NetworkManager {
	private final GameServer server;
	private final Socket socket;
	private final Queue<Packet> packetQueue = new LinkedList<Packet>();
	private final Thread readThread;
	private final Thread writeThread;
	
	public NetworkManager(GameServer server, Socket socket) {
		this.server = server;
		this.socket = socket;
		this.readThread = new ReaderThread(this);
        this.writeThread = new WriterThread(this);
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

	public Socket getSocket() {
		return socket;
	}
	
	public Queue<Packet> getPacketQueue() {
		return packetQueue;
	}

	public GameServer getServer() {
		return server;
	}
}