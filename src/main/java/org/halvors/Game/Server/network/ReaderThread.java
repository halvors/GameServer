package org.halvors.Game.Server.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.network.packet.IPacket;
import org.halvors.Game.Server.network.packet.PacketUtil;

public class ReaderThread extends Thread {
	private final GameServer server;
	private final NetworkManager networkManager;
	private final DataInputStream input;
	
	public ReaderThread(GameServer server, String name, NetworkManager networkManager) {
		super(name);
		
		this.server = server;
		this.networkManager = networkManager;
		this.input = networkManager.getDataInputStream();
	}
	
	@Override
	public void run() {
		IPacket packet = null;
		
		while (true) {
			try {
				packet = PacketUtil.readPacket(input);
				
				if (packet != null) {
					PacketUtil.handlePacket(packet, networkManager.getServerHandler());
					
					server.log(Level.INFO, "Packet with id: " + packet.getId() + " received.");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public GameServer getServer() {
		return server;
	}
	
	public NetworkManager getNetworkManager() {
		return networkManager;
	}
}