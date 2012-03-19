package org.halvors.Game.Server.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.network.packet.IPacket;
import org.halvors.Game.Server.network.packet.PacketUtil;

public class WriterThread extends Thread {
	private final GameServer server;
	private final NetworkManager networkManager;
	private final DataOutputStream output;
	
	public WriterThread(GameServer server, String name, NetworkManager networkManager) {
		super(name);
		
		this.server = server;
		this.networkManager = networkManager;
		this.output = networkManager.getDataOutputStream();
	}
	
	@Override
	public void run() {
		IPacket packet = null;
		
		while (true) {
			try {
				synchronized (networkManager.getPacketQueue()) {
					packet = networkManager.getPacketQueue().poll();
				}
				
				if (output != null && packet != null) {
					PacketUtil.writePacket(output, packet);
					
					server.log(Level.INFO, "Packet with id: " + packet.getId() + " sent.");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
