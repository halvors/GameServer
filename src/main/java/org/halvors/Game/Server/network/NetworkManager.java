package main.java.org.halvors.Game.Server.network;

import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import main.java.org.halvors.Game.Server.GameServer;
import main.java.org.halvors.Game.Server.LoginHandler;
import main.java.org.halvors.Game.Server.entity.Player;
import main.java.org.halvors.Game.Server.network.packet.Packet;

public class NetworkManager {
	private final Socket socket;
	private final Queue<Packet> packetQueue = new LinkedList<Packet>();
	private final LoginHandler loginHandler;
	private final NetworkServerHandler networkServerHandler;
	private final Thread readThread;
	private final Thread writeThread;
	
	public NetworkManager(Socket socket, LoginHandler loginHandler) {
		this.socket = socket;
		this.loginHandler = loginHandler;
		this.networkServerHandler = new NetworkServerHandler(GameServer.getInstance(), this);
		this.readThread = new NetworkReaderThread(this);
        this.writeThread = new NetworkWriterThread(this);
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
	public void sendPacketToAll(Packet packet) {
		for (Player player : GameServer.getInstance().getPlayers()) {
			player.getNetworkManager().sendPacket(packet);
		}
	}

	public Socket getSocket() {
		return socket;
	}
	
	public Queue<Packet> getPacketQueue() {
		return packetQueue;
	}

	public LoginHandler getLoginHandler() {
		return loginHandler;
	}
	
	public NetworkServerHandler getNetworkServerHandler() {
		return networkServerHandler;
	}
}